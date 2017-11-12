package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.*;

/**
 * @author ustits
 */
@Log4j2
public class ArgsAwareCommandTest {

  private static final int MIN_ARGS_BOUND = 10;

  private ArgsAwareCommand command;
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

  private ArgsAwareCommand mockCommand() {
    final BotCommand command = new BotCommand(string(), string()) {
      @Override
      public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
        log.info("Executing");
      }
    };
    return new ArgsAwareCommand(command, minArgs);
  }

}