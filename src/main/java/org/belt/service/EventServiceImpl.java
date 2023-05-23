package org.belt.service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.belt.engine.EventEngine;
import org.belt.engine.EventEngineImpl;
import org.belt.model.Belt;
import org.belt.model.BeltSegment;
import org.belt.model.Event;
import org.belt.model.Sensor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventServiceImpl implements EventService {
  EventEngine eventEngine;

  public EventServiceImpl() {
    Belt belt = getBelt();
    eventEngine = new EventEngineImpl(belt);
  }

  private Belt getBelt() {
    List<BeltSegment> beltSegments = getBeltSegments();
    return new Belt("BELT1", 2L, beltSegments);
  }

  private List<BeltSegment> getBeltSegments() {
    List<BeltSegment> beltSegments = new ArrayList<>();
    BeltSegment bs1 = new BeltSegment("BS1", 2, new Sensor("S0"), new Sensor("S1"), 0, 0) ;
    BeltSegment bs2 = new BeltSegment("BS2", 6, new Sensor("S1"), new Sensor("S2"), 0, 0) ;
    BeltSegment bs3 = new BeltSegment("BS3", 3, new Sensor("S2"), new Sensor("S3"), 0, 0) ;
    beltSegments.add(bs1);
    beltSegments.add(bs2);
    beltSegments.add(bs3);
    return beltSegments;
  }

  @Override
  public void process(Event event) {
    log.info("Processing event=" + event);
    eventEngine.process(event);
    log.info("Completed processing event=" + event);
  }
}
