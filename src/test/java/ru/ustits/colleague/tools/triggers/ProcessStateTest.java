package ru.ustits.colleague.tools.triggers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ProcessStateTest {

  @Test
  public void testHas() {
    final String state = ProcessState.ALL.getName();
    assertThat(ProcessState.has(state)).isTrue();
  }

  @Test
  public void testHasNot() {
    final String state = string();
    assertThat(ProcessState.has(state)).isFalse();
  }

  @Test
  public void testHasNotIfStateIsNull() {
    assertThat(ProcessState.has(null)).isFalse();
  }

}