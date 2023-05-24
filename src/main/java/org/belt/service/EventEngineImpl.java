package org.belt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import lombok.extern.slf4j.Slf4j;
import org.belt.model.Alarm;
import org.belt.model.AlarmType;
import org.belt.model.Belt;
import org.belt.model.BeltSegment;
import org.belt.model.Event;
import org.belt.model.ProcessResult;
import org.belt.task.MonitorClearanceTask;

@Slf4j
public class EventEngineImpl implements EventEngine {

  public final long MAX_ITEMS_ON_BELT = 10;

  Belt belt;

  public EventEngineImpl(Belt belt) {
    this.belt = belt;
  }

  @Override
  public ProcessResult process(Event event) {
    log.info("Processing event=" + event);
    update(event);
    List<Alarm> alarms = new ArrayList<>();
    long itemsInBelt = getItemsInBelt();
    if (itemsInBelt >= MAX_ITEMS_ON_BELT) {
      alarms.add(
          new Alarm(AlarmType.MAX_ITEMS_ON_BELT, "The number of items in belt is " + itemsInBelt));
    }
    ProcessResult processResult = new ProcessResult(alarms, "Processed event=" + event);
    log.info("Completed processing event=" + event);
    return processResult;
  }

  public void update(Event event) {
    List<BeltSegment> segmentList = belt.getSegmentList();
    int segmentIndex = 0;
    for (BeltSegment beltSegment : segmentList) {
      if (beltSegment.getOutSensor().getId().equals(event.getSensorId())) {
        beltSegment.setOutEventCounter(beltSegment.getOutEventCounter() + 1);
      }
      if (beltSegment.getInSensor().getId().equals(event.getSensorId())) {
        beltSegment.setInEventCounter(beltSegment.getInEventCounter() + 1);
        // Create a timer to check item that triggered the event is not stuck
        // after the expected clearance time for a segment
        double clearanceTimeInSeconds = getClearanceTimeInSeconds(beltSegment);
        long outEventCounter = beltSegment.getOutEventCounter();
        MonitorClearanceTask monitorClearanceTask = new MonitorClearanceTask(outEventCounter, belt,
            segmentIndex);
        Timer timer = new Timer("MonitorClearanceTaskTimer");
        timer.schedule(monitorClearanceTask, (100 + (long) (clearanceTimeInSeconds * 1000)));
        log.info("Created monitor clearance task for segment " + beltSegment.getName()
            + " with clearanceTime=" + clearanceTimeInSeconds + "s");
      }
      segmentIndex++;
    }
  }

  public long getItemsInSegment(BeltSegment segment) {
    return (segment.getInEventCounter() - segment.getOutEventCounter());
  }

  public long getItemsInBelt() {
    long itemNumber = 0;
    for (BeltSegment beltSegment : belt.getSegmentList()) {
      itemNumber = itemNumber + getItemsInSegment(beltSegment);
    }
    return itemNumber;
  }

  public double getClearanceTimeInSeconds(BeltSegment segment) {
    double clearanceTime = (((double) segment.getLength()) / belt.getMovementSpeed());
    log.info("Clearance time for segment " + segment.getName() + " is " + clearanceTime + "s");
    return clearanceTime;
  }
}
