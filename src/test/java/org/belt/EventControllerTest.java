package org.belt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import org.belt.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port + "/api/v1";
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllEvents() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/events",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEventById() {
		Event event = restTemplate.getForObject(getRootUrl() + "/events/1", Event.class);
		assertNotNull(event);
		System.out.println("Get event=" + event);
	}

	@Test
	public void testCreateEvent() {
		Event event = new Event();
		event.setSensorId("S0");
		event.setTime(Instant.now().toEpochMilli());

		ResponseEntity<Event> postResponse = restTemplate.postForEntity(getRootUrl() + "/events", event, Event.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
		System.out.println("Created event=" + postResponse.getBody());
	}

	@Test
	public void testDeleteEvent() {
		int id = 2;
		Event event = restTemplate.getForObject(getRootUrl() + "/events/" + id, Event.class);
		assertNotNull(event);

		restTemplate.delete(getRootUrl() + "/events/" + id);

		try {
			event = restTemplate.getForObject(getRootUrl() + "/events/" + id, Event.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
