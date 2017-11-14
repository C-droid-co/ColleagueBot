package ru.ustits.colleague.tools.triggers;

import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class NoTriggersTest {

  @Test
  public void testProcess() throws Exception {
    final NoTriggers strategy = new NoTriggers();
    final List<SendMessage> messages = Arrays.asList(new SendMessage(), new SendMessage(), new SendMessage());
    assertThat(strategy.process(messages)).isEmpty();
  }
}