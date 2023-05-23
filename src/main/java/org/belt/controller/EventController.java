package org.belt.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.belt.error.ResourceNotFoundException;
import org.belt.model.Event;
import org.belt.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EventController {

  @Autowired
  private EventRepository eventRepository;

  @GetMapping("/events")
  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable(value = "id") Long id)
      throws ResourceNotFoundException {
    Event event = eventRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id :: " + id));
    return ResponseEntity.ok().body(event);
  }

  @PostMapping("/events")
  public Event createEvent(@Valid @RequestBody Event event) {
    return eventRepository.save(event);
  }

  @PutMapping("/events/{id}")
  public ResponseEntity<Event> updateEvent(@PathVariable(value = "id") Long eventId,
      @Valid @RequestBody Event eventDetails) throws ResourceNotFoundException {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Event not found for this id :: " + eventId));

    event.setSource(eventDetails.getSource());
    event.setTime(eventDetails.getTime());
    final Event updatedEvent = eventRepository.save(event);
    return ResponseEntity.ok(updatedEvent);
  }

  @DeleteMapping("/events/{id}")
  public Map<String, Boolean> deleteEvent(@PathVariable(value = "id") Long eventId)
      throws ResourceNotFoundException {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Event not found for this id :: " + eventId));

    eventRepository.delete(event);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
