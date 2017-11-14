package ru.ustits.colleague.tools.triggers;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class PeriodicTriggerTest {

  private List<SendMessage> messages;

  @Before
  public void setUp() throws Exception {
    messages = Arrays.asList(new SendMessage(), new SendMessage(), new SendMessage());
  }

  @Test
  public void testProcessReturnsTrigger() throws Exception {
    final PeriodicTrigger strategy = new PeriodicTrigger(Integer.MIN_VALUE);
    assertThat(strategy.process(messages)).isNotEmpty();
  }

  @Test
  public void testProcessReturnsNothing() throws Exception {
    final PeriodicTrigger strategy = new PeriodicTrigger(Integer.MAX_VALUE);
    assertThat(strategy.process(messages)).isEmpty();
  }
}