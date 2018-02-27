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
public class RandomTriggerTest {

  private RandomTrigger strategy;

  @Before
  public void setUp() throws Exception {
    strategy = new RandomTrigger();
  }

  @Test
  public void testProcess() {
    final List<String> values = Arrays.asList(string(), string(), string());
    assertThat(strategy.process(values)).hasSize(1);
  }

  @Test
  public void testProcessWithEmptyList() {
    final List<String> empty = new ArrayList<>();
    assertThat(strategy.process(empty)).isEmpty();
  }

}