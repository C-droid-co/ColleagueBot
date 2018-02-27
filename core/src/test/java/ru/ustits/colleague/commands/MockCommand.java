package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
@Log4j2
public final class MockCommand extends BotCommand {

  public MockCommand() {
    super(string(), string());
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    log.info("Executing");
  }
}
