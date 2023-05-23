package org.belt.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.belt.model.Belt;
import org.belt.model.BeltSegment;
import org.belt.model.Event;
import org.belt.model.Sensor;
import org.belt.service.EventEngine;
import org.belt.service.EventEngineImpl;
import org.junit.jupiter.api.Test;

public class EventEngineImplTest {

  @Test
  public void updateTest() {
    Belt belt = getBelt();
    EventEngine engine = new EventEngineImpl(belt);
    engine.process(new Event("S0", 1000));
    engine.process(new Event("S0", 1200));
    engine.process(new Event("S0", 1300));
    engine.process(new Event("S1", 2000));
    engine.process(new Event("S1", 2200));
    engine.process(new Event("S2", 3000));
    assertEquals(belt.getSegmentList().get(0).getInEventCounter(), 3);
    assertEquals(belt.getSegmentList().get(0).getOutEventCounter(), 2);
    assertEquals(belt.getSegmentList().get(1).getInEventCounter(), 2);
    assertEquals(belt.getSegmentList().get(1).getOutEventCounter(), 1);
    assertEquals(belt.getSegmentList().get(2).getInEventCounter(), 1);
    assertEquals(belt.getSegmentList().get(2).getOutEventCounter(), 0);
  }

  @Test
  public void getItemsInSegmentTest() {
    Belt belt = getBelt();
    EventEngineImpl engine = new EventEngineImpl(belt);
    engine.process(new Event("S0", 1000));
    engine.process(new Event("S0", 1200));
    engine.process(new Event("S0", 1300));
    engine.process(new Event("S1", 2000));
    engine.process(new Event("S1", 2200));
    engine.process(new Event("S2", 3000));
    BeltSegment bs1 = new BeltSegment("BS1", 2, new Sensor("S0"), new Sensor("S1"), 34, 29);
    assertEquals(5, engine.getItemsInSegment(bs1));
  }

  @Test
  public void getItemsInBeltTest() {
    Belt belt = getBelt();
    EventEngineImpl engine = new EventEngineImpl(belt);
    engine.process(new Event("S0", 1000));
    engine.process(new Event("S0", 1200));
    engine.process(new Event("S0", 1300));
    engine.process(new Event("S0", 1500));
    engine.process(new Event("S0", 1700));
    engine.process(new Event("S1", 2000));
    engine.process(new Event("S1", 2200));
    engine.process(new Event("S2", 3000));
    assertEquals(5, engine.getItemsInBelt());
  }

  private Belt getBelt() {
    List<BeltSegment> beltSegments = getBeltSegments();
    return new Belt("BELT1", 2L, beltSegments);
  }


  private List<BeltSegment> getBeltSegments() {
    List<BeltSegment> beltSegments = new ArrayList<>();
    BeltSegment bs1 = new BeltSegment("BS1", 2, new Sensor("S0"), new Sensor("S1"), 0, 0);
    BeltSegment bs2 = new BeltSegment("BS2", 6, new Sensor("S1"), new Sensor("S2"), 0, 0);
    BeltSegment bs3 = new BeltSegment("BS3", 3, new Sensor("S2"), new Sensor("S3"), 0, 0);
    beltSegments.add(bs1);
    beltSegments.add(bs2);
    beltSegments.add(bs3);
    return beltSegments;
  }

}
