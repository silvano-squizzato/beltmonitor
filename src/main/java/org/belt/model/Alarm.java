package org.belt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alarm {
  AlarmType alarmType;
  String description;
}
