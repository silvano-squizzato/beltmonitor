package org.belt.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A belt is characterised by:
 * - name
 * - uniform movement speed
 * - list of contiguous segments
 */
@AllArgsConstructor
@Data
public class Belt {
  String name;
  Long movementSpeed;
  List<BeltSegment> segmentList = new ArrayList<>();
}
