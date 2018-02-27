package ru.ustits.colleague.triggers.commands;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.*;

/**
 * @author ustits
 */
public class ListTriggersCommandTest {

  private ListTriggersCommand command;

  @Before
  public void setUp() throws Exception {
    command = new ListTriggersCommand(string(), mock(TriggerRepository.class));
  }

  @Test
  public void testToMessages() throws Exception {
    final String trigger = string();
    final String message = string();
    final TriggerRecord record = new TriggerRecord(anInt(),
            trigger, message, aLong(), aLong());
    final List<String> result = command.toMessages(Collections.singletonList(record));
    assertThat(result).isNotEmpty();
  }

  @Test
  public void testToMessagesWithEmptyList() throws Exception {
    final List<String> result = command.toMessages(Collections.emptyList());
    assertThat(result).containsOnly(ListTriggersCommand.NO_TRIGGER_RESULT);
  }

  @Test
  public void testToMessagesWithNullList() throws Exception {
    final List<String> result = command.toMessages(null);
    assertThat(result).containsOnly(ListTriggersCommand.NO_TRIGGER_RESULT);
  }

}