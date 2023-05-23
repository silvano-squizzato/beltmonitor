package org.belt.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.belt.error.ResourceNotFoundException;
import org.belt.model.Event;
import org.belt.service.PersistenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  public EventController(PersistenceService persistenceService) {
    this.persistenceService = persistenceService;
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
      throw new ResourceNotFoundException("Event not found for this id :: " + id);
    }
    return ResponseEntity.ok().body(event);
  }

  @PostMapping("/events")
  public Event createEvent(@Valid @RequestBody Event event) {
    log.info("Creating event=" + event);
    return persistenceService.saveEvent(event);
  }

  @DeleteMapping("/events/{id}")
  public Map<String, Boolean> deleteEvent(@PathVariable(value = "id") Long id) {
    log.info("Deleting eventId=" + id);
    persistenceService.deleteEvent(id);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
