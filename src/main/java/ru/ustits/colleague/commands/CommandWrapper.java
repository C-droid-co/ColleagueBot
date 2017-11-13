package ru.ustits.colleague.commands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

/**
 * @author ustits
 */
public class CommandWrapper extends BotCommand {

  private final BotCommand innerCommand;

  public CommandWrapper(final BotCommand innerCommand) {
    super(innerCommand.getCommandIdentifier(), innerCommand.getDescription());
    this.innerCommand = innerCommand;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    innerCommand.execute(absSender, user, chat, arguments);
  }

}
