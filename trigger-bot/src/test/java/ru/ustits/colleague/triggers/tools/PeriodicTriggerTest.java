package ru.ustits.colleague.triggers.tools;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class PeriodicTriggerTest {

  private List<String> messages;

  @Before
  public void setUp() throws Exception {
    messages = Arrays.asList(string(), string(), string());
  }

  @Test
  public void testProcessReturnsTrigger() {
    final PeriodicTrigger strategy = new PeriodicTrigger(Integer.MIN_VALUE);
    assertThat(strategy.process(messages)).isNotEmpty();
  }

  @Test
  public void testProcessReturnsNothing() {
    final PeriodicTrigger strategy = new PeriodicTrigger(Integer.MAX_VALUE);
    assertThat(strategy.process(messages)).isEmpty();
  }

}