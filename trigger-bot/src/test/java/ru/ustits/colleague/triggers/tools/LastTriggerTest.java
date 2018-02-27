package ru.ustits.colleague.triggers.tools;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

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
  public void testProcess() {
    final String last = string();
    final List<String> messages = Arrays.asList(string(), string(), last);
    assertThat(strategy.process(messages)).containsOnly(last);
  }

  @Test
  public void testProcessWithEmptyList() {
    final List<String> messages = new ArrayList<>();
    assertThat(strategy.process(messages)).isEmpty();
  }

}