package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import ru.ustits.colleague.repositories.TriggerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.*;

/**
 * @author ustits
 */
public class AbstractTriggerCommandTest {

  private static final int MIN_ARGS_BOUND = 10;

  private AbstractTriggerCommand command;
  private int minArgs;

  @Before
  public void setUp() throws Exception {
    minArgs = anInt(MIN_ARGS_BOUND) + 1;
    command = mockCommand();
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

  @Test
  public void testResolveTrigger() throws Exception {
    final String trigger = string();
    final String[] args = new String[]{trigger, string()};
    assertThat(command.resolveTrigger(args)).isEqualTo(trigger.toLowerCase());
  }

  private AbstractTriggerCommand mockCommand() {
    return new AbstractTriggerCommand(string(), string(), mock(TriggerRepository.class), minArgs) {
      @Override
      protected void executeInternal(final AbsSender absSender, final User user, final Chat chat,
                                     final String[] arguments) {
      }
    };
  }


}