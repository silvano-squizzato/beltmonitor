package org.belt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Event {

  private long id;
  private long time;
  private String sensorId;

  public Event() {
  }

  public Event(String sensorId, long time) {
    this.sensorId = sensorId;
    this.time = time;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
  }

  @Column(name = "sensorId", nullable = false)
  public String getSensorId() {
    return sensorId;
  }

  @Column(name = "time", nullable = false)
  public long getTime() {
    return time;
  }
}
