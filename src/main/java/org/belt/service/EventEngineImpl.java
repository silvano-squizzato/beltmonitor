package org.belt.service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.belt.model.Alarm;
import org.belt.model.AlarmType;
import org.belt.model.Belt;
import org.belt.model.BeltSegment;
import org.belt.model.Event;
import org.belt.model.ProcessResult;

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
    for (BeltSegment beltSegment : segmentList) {
      if (beltSegment.getInSensor().getId().equals(event.getSensorId())) {
        beltSegment.setInEventCounter(beltSegment.getInEventCounter() + 1);
      }
      if (beltSegment.getOutSensor().getId().equals(event.getSensorId())) {
        beltSegment.setOutEventCounter(beltSegment.getOutEventCounter() + 1);
      }
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
}
