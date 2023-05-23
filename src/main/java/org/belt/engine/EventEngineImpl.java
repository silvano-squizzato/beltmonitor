package org.belt.engine;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.belt.model.Belt;
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
    ProcessResult processResult = new ProcessResult(new ArrayList<>(), "Processed event=" + event);
    log.info("Completed processing event=" + event);
    return processResult;
  }
}
