package org.belt.engine;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.belt.model.Belt;
import org.belt.model.BeltSegment;
import org.belt.model.Event;
import org.belt.model.ProcessResult;

@Slf4j
public class EventEngineImpl implements EventEngine {

  Belt belt;

  public EventEngineImpl(Belt belt) {
    this.belt = belt;
  }

  @Override
  public ProcessResult process(Event event) {
    log.info("Processing event=" + event);
    update(event);
    ProcessResult processResult = new ProcessResult(new ArrayList<>(), "Processed event=" + event);
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
}
