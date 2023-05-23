package org.belt.service;

import org.belt.model.Event;
import org.belt.model.ProcessResult;

public interface EventEngine {
  ProcessResult process(Event event);
}
