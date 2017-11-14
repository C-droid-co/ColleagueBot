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
public class RandomTriggerTest {

  private RandomTrigger strategy;

  @Before
  public void setUp() throws Exception {
    strategy = new RandomTrigger();
  }

  @Test
  public void testProcess() throws Exception {
    final List<SendMessage> values = Arrays.asList( new SendMessage(), new SendMessage(), new SendMessage());
    assertThat(strategy.process(values)).hasSize(1);
  }

  @Test
  public void testProcessWithEmptyList() throws Exception {
    final List<SendMessage> empty = new ArrayList<>();
    assertThat(strategy.process(empty)).isEmpty();
  }
}