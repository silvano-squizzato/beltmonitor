package org.belt.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.belt.error.ResourceNotFoundException;
import org.belt.model.Event;
import org.belt.service.EventService;
import org.belt.service.PersistenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class EventController {

  PersistenceService persistenceService;
  EventService eventService;

  public EventController(PersistenceService persistenceService, EventService eventService) {
    this.persistenceService = persistenceService;
    this.eventService = eventService;
  }

  @GetMapping("/events")
  public List<Event> getAllEvents() {
    log.info("Getting all events");
    return persistenceService.getEvents();
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable(value = "id") Long id)
      throws ResourceNotFoundException {
    log.info("Getting eventId=" + id);
    Event event = persistenceService.getEventById(id);
    if (event == null) {
      throw new ResourceNotFoundException("Event not found for id=" + id);
    }
    return ResponseEntity.ok().body(event);
  }

  @PostMapping("/events")
  public Event createEvent(@Valid @RequestBody Event event) {
    log.info("Creating event=" + event);
    Event savedEvent = persistenceService.saveEvent(event);
    eventService.process(savedEvent);
    return savedEvent;
  }
}
