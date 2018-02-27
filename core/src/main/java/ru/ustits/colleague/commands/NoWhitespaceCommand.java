package ru.ustits.colleague.commands;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ustits
 */
public final class NoWhitespaceCommand extends CommandWrapper {

  public NoWhitespaceCommand(final BotCommand innerCommand) {
    super(innerCommand);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    super.execute(absSender, user, chat, removeWhitespaces(arguments));
  }

  String[] removeWhitespaces(final String[] arguments) {
    final List<String> processedArgs = new ArrayList<>();
    for (final String arg : arguments) {
      if (!StringUtils.isWhitespace(arg)) {
        processedArgs.add(arg);
      }
    }
    return processedArgs.toArray(new String[0]);
  }
}
