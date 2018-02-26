package ru.ustits.colleague.tools.triggers;

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
public class FirstTriggerTest {

  private FirstTrigger strategy;

  @Before
  public void setUp() throws Exception {
    strategy = new FirstTrigger();
  }

  @Test
  public void testProcess() throws Exception {
    final String first = string();
    final List<String> messages = Arrays.asList(first, string(), string());
    assertThat(strategy.process(messages)).containsOnly(first);
  }

  @Test
  public void testProcessWithEmptyList() throws Exception {
    final List<String> messages = new ArrayList<>();
    assertThat(strategy.process(messages)).isEmpty();
  }

}