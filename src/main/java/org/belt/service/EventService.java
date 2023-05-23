package org.belt.service;

import org.belt.model.Belt;
import org.belt.model.Event;

public interface EventService {
  void process(Event event);
}
