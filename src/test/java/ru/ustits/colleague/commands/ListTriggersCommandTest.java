package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class ListTriggersCommandTest {

  private ListTriggersCommand command;

  @Before
  public void setUp() throws Exception {
    command = new ListTriggersCommand("random");
  }

  @Test
  public void testRecordsToString() throws Exception {
    final String trigger = "trigger";
    final String message = "message";
    final TriggerRecord record = new TriggerRecord(1,
            trigger, message, 1L, 1L);
    final String result = command.recordsToString(Collections.singletonList(record));
    assertThat(result).isEqualTo(trigger + " : " + message + "\n");
  }

  @Test
  public void testRecordsToStringWithEmptyList() throws Exception {
    final String result = command.recordsToString(Collections.emptyList());
    assertThat(result).isEqualTo(ListTriggersCommand.NO_TRIGGER_RESULT);
  }

  @Test
  public void testRecordsToStringWithNullList() throws Exception {
    final String result = command.recordsToString(null);
    assertThat(result).isEqualTo(ListTriggersCommand.NO_TRIGGER_RESULT);
  }
}