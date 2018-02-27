package ru.ustits.colleague.triggers.tools;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class NoTriggersTest {

  @Test
  public void testProcess() throws Exception {
    final NoTriggers strategy = new NoTriggers();
    final List<String> messages = Arrays.asList(string(), string(), string());
    assertThat(strategy.process(messages)).isEmpty();
  }
}