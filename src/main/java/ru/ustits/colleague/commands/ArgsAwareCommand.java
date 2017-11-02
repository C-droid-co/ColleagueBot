package ru.ustits.colleague.commands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author ustits
 */
@Log4j2
@Getter(AccessLevel.PROTECTED)
public abstract class ArgsAwareCommand extends BotCommand {

  private final int minArgsLen;

  public ArgsAwareCommand(final String commandIdentifier, final String description,
                          final int minArgsLen) {
    super(commandIdentifier, description);
    this.minArgsLen = minArgsLen;
  }

  @Override
  public final void execute(final AbsSender absSender, final User user, final Chat chat,
                            final String[] arguments) {
    if (enough(arguments)) {
      executeInternal(absSender, user, chat, arguments);
    } else {
      final SendMessage fail = new SendMessage(chat.getId(), "Not enough arguments passed");
      try {
        absSender.execute(fail);
      } catch (TelegramApiException e) {
        log.error("Unable to send fail message", e);
      }
    }
  }

  protected abstract void executeInternal(final AbsSender absSender, final User user, final Chat chat,
                                          final String[] arguments);

  protected final boolean enough(final String[] arguments) {
    return arguments != null && arguments.length >= minArgsLen;
  }
}
