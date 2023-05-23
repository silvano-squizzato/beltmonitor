package org.belt.engine;

import org.belt.model.Belt;
import org.belt.model.Event;
import org.belt.model.ProcessResult;

public interface EventEngine {
  ProcessResult process(Event event);
}
