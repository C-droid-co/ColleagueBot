package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aPositiveInt;
import static ru.ustits.colleague.RandomUtils.values;

/**
 * @author ustits
 */
public class ArgsAwareCommandTest {

  private static final int MIN_ARGS_BOUND = 10;

  private ArgsAwareCommand command;
  private int minArgs;

  @Before
  public void setUp() throws Exception {
    minArgs = aPositiveInt(MIN_ARGS_BOUND) + 1;
    command = new ArgsAwareCommand(new MockCommand(), minArgs);
  }

  @Test
  public void testEnough() throws Exception {
    final String[] args = values(minArgs + 1);
    assertThat(command.enough(args)).isTrue();
  }

  @Test
  public void testNotEnough() throws Exception {
    final String[] args = values(minArgs - 1);
    assertThat(command.enough(args)).isFalse();
  }

  @Test
  public void testEnoughWithNull() throws Exception {
    assertThat(command.enough(null)).isFalse();
  }

}