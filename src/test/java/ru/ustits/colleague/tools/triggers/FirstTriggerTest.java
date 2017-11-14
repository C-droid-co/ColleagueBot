package ru.ustits.colleague.tools.triggers;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class FirstTriggerTest {

  private FirstTrigger strategy;

  @Before
  public void setUp() throws Exception {
    strategy = new FirstTrigger();
  }

  @Test
  public void testProcess() throws Exception {
    final SendMessage first = new SendMessage();
    final List<SendMessage> messages = Arrays.asList(first, new SendMessage(), new SendMessage());
    assertThat(strategy.process(messages)).containsOnly(first);
  }

  @Test
  public void testProcessWithEmptyList() throws Exception {
    final List<SendMessage> messages = new ArrayList<>();
    assertThat(strategy.process(messages)).isEmpty();
  }

}