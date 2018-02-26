package ru.ustits.colleague.tools.triggers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ProcessStateTest {

  @Test
  public void testToState() {
    final ProcessState processState = ProcessState.NOTHING;
    final String state = processState.getName();
    assertThat(ProcessState.toState(state))
            .isPresent()
            .hasValue(processState);
  }

  @Test
  public void testToStateIsEmpty() {
    final String state = string();
    assertThat(ProcessState.toState(state))
            .isNotPresent();
  }

  @Test
  public void testToStateIsEmptyWithPassedNull() {
    assertThat(ProcessState.toState(null))
            .isNotPresent();
  }

}