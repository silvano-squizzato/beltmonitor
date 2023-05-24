package org.belt.task;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.belt.model.Alarm;
import org.belt.model.AlarmType;
import org.belt.model.Belt;

@Slf4j
public class MonitorClearanceTask extends TimerTask {

  long outEventCounter;
  int segmentIndex;
  Belt belt;

  public MonitorClearanceTask(long outEventCounter, Belt belt, int segmentIndex) {
    this.segmentIndex = segmentIndex;
    this.outEventCounter = outEventCounter;
    this.belt = belt;
  }

  @Override
  public void run() {
    boolean result = isSegmentCleared();
    if (!result) {
      Alarm alarm = new Alarm(AlarmType.ITEM_STUCK,
          "The are items stuck on segment " + belt.getSegmentList().get(segmentIndex).getName());
      log.warn("Alarm detected of type=" + alarm.getAlarmType() + " with description="
          + alarm.getDescription());
    }
    LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()),
        ZoneId.systemDefault());
    log.info(
        "Task run on segment " + belt.getSegmentList().get(segmentIndex).getName() + " with result "
            + result + " at time=" + time);
  }

  private boolean isSegmentCleared() {
    boolean result = false;
    if (belt.getSegmentList().get(segmentIndex).getOutEventCounter() > outEventCounter) {
      result = true;
    }
    return result;
  }
}
