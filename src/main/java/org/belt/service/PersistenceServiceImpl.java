package org.belt.service;

import java.util.ArrayList;
import java.util.List;
import org.belt.model.Event;
import org.belt.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class PersistenceServiceImpl implements PersistenceService {

  EventRepository repository;

  public PersistenceServiceImpl(EventRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Event> getEvents() {
    List<Event> events = new ArrayList<>();
    repository.findAll().forEach(events::add);
    return events;
  }

  @Override
  public Event getEventById(Long id) {
    return repository.findById(id).get();
  }

  @Override
  public Event saveEvent(Event event) {
    return repository.save(event);
  }

  @Override
  public Event getLastEventBySensorId(String sensorId) {
    List<Event> events = repository.findFirstBySensorIdOrderByTimeDesc(sensorId);
    return events.get(0);
  }
}
