package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.tools.triggers.ProcessStates;

/**
 * @author ustits
 */
@Log4j2
public final class ListProcessStatesCommand extends BotCommand {

  public ListProcessStatesCommand(final String commandIdentifier, final String description) {
    super(commandIdentifier, description);
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final StringBuilder builder = new StringBuilder();
    builder.append("*Available states:*");
    for (final ProcessStates state : ProcessStates.values()) {
      builder.append("\n").append("- ").append(state.getName());
    }
    try {
      absSender.execute(new SendMessage(chat.getId(), builder.toString()).enableMarkdown(true));
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }
}
