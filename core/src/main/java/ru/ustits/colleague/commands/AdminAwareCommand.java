package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class AdminAwareCommand extends CommandWrapper {

  private final Long adminId;

  public AdminAwareCommand(final BotCommand innerCommand, final Long adminId) {
    super(innerCommand);
    this.adminId = adminId;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final Long userId = toUnsignedLong(user.getId());
    if (isAdmin(userId)) {
      super.execute(absSender, user, chat, arguments);
    } else {
      try {
        absSender.execute(new SendMessage(chat.getId(), "You have no admin rights"));
      } catch (TelegramApiException e) {
        log.error("Unable to send message", e);
      }
    }
  }

  boolean isAdmin(final Long userId) {
    return userId != null && userId.equals(adminId);
  }
}
