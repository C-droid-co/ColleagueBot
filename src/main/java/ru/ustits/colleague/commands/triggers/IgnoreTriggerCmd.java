package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.IgnoreTriggerRepository;
import ru.ustits.colleague.repositories.records.IgnoreTriggerRecord;

import static java.lang.Integer.toUnsignedLong;

/**
 * @author ustits
 */
@Log4j2
public final class IgnoreTriggerCmd extends BotCommand {

  private final IgnoreTriggerRepository repository;

  public IgnoreTriggerCmd(final String commandIdentifier, final IgnoreTriggerRepository repository) {
    super(commandIdentifier, "bot will ignore your messages");
    this.repository = repository;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat,
                      final String[] arguments) {
    final Long userId = toUnsignedLong(user.getId());
    final Long chatId = chat.getId();
    final IgnoreTriggerRecord record = new IgnoreTriggerRecord(chatId, userId);
    final SendMessage answer = new SendMessage().setChatId(chatId);
    if (repository.existsByChatIdAndUserId(record.getChatId(), record.getUserId())) {
      repository.delete(record);
      log.info("Deleted [{}] in chat [{}] from ignore_triggers table", userId, chatId);
      answer.setText("You are now not ignored by triggers");
    } else {
      repository.save(record);
      log.info("Added [{}] in chat [{}] to ignore_triggers table", userId, chatId);
      answer.setText("You are now ignored by triggers");
    }
    try {
      absSender.execute(answer);
    } catch (TelegramApiException e) {
      log.error("Unable to send message", e);
    }
  }

}
