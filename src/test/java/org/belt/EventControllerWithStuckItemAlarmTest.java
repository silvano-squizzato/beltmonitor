package org.belt;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.belt.model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerWithStuckItemAlarmTest {

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
  public void testItemStuckAlarm() {
    System.out.println("Start testItemStuckAlarm");
    try {
      Event event = new Event();
      event.setSensorId("S0");
      event.setTime(Instant.now().toEpochMilli());
      send(event);
      TimeUnit.MILLISECONDS.sleep(3000);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    System.out.println("End testItemStuckAlarm");
  }

  @Test
  public void testItemWithFirstItemClearedAndSecondItemStuck() {
    System.out.println("Start testItemStuckAlarm");
    try {
      Event event = new Event();
      event.setSensorId("S0");
      event.setTime(Instant.now().toEpochMilli());
      send(event);
      TimeUnit.MILLISECONDS.sleep(400);
      event = new Event();
      event.setSensorId("S1");
      event.setTime(Instant.now().toEpochMilli());
      send(event);
      TimeUnit.MILLISECONDS.sleep(5000);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    System.out.println("End testItemStuckAlarm");
  }

  private void send(Event event) {
    ResponseEntity<Event> postResponse = restTemplate.postForEntity(getRootUrl() + "/events", event,
        Event.class);
    System.out.println("Sent event=" + postResponse.getBody());
  }
}
