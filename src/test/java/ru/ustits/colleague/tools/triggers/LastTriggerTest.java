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
public class LastTriggerTest {

  private LastTrigger strategy;

  @Before
  public void setUp() throws Exception {
    strategy = new LastTrigger();
  }

  @Test
  public void testProcess() throws Exception {
    final SendMessage last = new SendMessage();
    final List<SendMessage> messages = Arrays.asList(new SendMessage(), new SendMessage(), last);
    assertThat(strategy.process(messages)).containsOnly(last);
  }

  @Test
  public void testProcessWithEmptyList() throws Exception {
    final List<SendMessage> messages = new ArrayList<>();
    assertThat(strategy.process(messages)).isEmpty();
  }

}