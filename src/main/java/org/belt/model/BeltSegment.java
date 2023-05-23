package org.belt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A segment is a portion of a conveyor belt characterised by:
 * - name
 * - sensor at the beginning where parcels come in
 * - sensor at the end where parcels come out
 * - length
 * - counter for events detected by sensor inSensor
 * - counter for events detected by sensor outSensor
 */
@Data
@AllArgsConstructor
public class BeltSegment {
  String name;
  int length;
  Sensor inSensor;
  Sensor outSensor;
  long inEventCounter = 0;
  long outEventCounter = 0;
}
