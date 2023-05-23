package org.belt.service;

import java.util.List;
import org.belt.model.Event;

public interface PersistenceService {

  List<Event> getEvents();

  Event getEventById(Long id);

  Event saveEvent(Event event);

  void deleteEvent(Long id);
}
