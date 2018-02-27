package ru.ustits.colleague.triggers.commands;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.*;

/**
 * @author ustits
 */
public class AddTriggerCommandTest {

  private AddTriggerCommand command;
  private int messageLength;

  @Before
  public void setUp() throws Exception {
    final TriggerCmdConfig config = new TriggerCmdConfig();
    messageLength = aPositiveInt();
    config.setMessageLength(messageLength);
    command = new AddTriggerCommand(string(), string(),
            mock(TriggerRepository.class), new TriggerParser(anInt()),
            config);
  }

  @Test
  public void testIsMessageToLong() {
    final TriggerRecord record = new TriggerRecord(string(), string(messageLength + 1), aLong(), aLong());
    final boolean result = command.isMessageTooLong(record);
    assertThat(result).isTrue();
  }

  @Test
  public void testMessageIsNotTooLong() {
    final TriggerRecord record = new TriggerRecord(string(), string(messageLength), aLong(), aLong());
    final boolean result = command.isMessageTooLong(record);
    assertThat(result).isFalse();
  }

}