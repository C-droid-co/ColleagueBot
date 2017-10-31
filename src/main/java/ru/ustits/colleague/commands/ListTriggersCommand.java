package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.List;

/**
 * @author ustits
 */
@Log4j2
public final class ListTriggersCommand extends BotCommand {

  static final String NO_TRIGGER_RESULT = "No triggers for current chat";

  @Autowired
  private TriggerRepository repository;

  public ListTriggersCommand(final String commandIdentifier) {
    super(commandIdentifier, "list triggers");
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final Long chatId = chat.getId();
    final List<TriggerRecord> triggers = repository.fetchAll(chatId);
    final String text = String.format("*Current triggers:* %n```%n%s%n```", recordsToString(triggers));
    final SendMessage message = new SendMessage(chatId, text);
    message.enableMarkdown(true);
    try {
      absSender.execute(message);
    } catch (TelegramApiException e) {
      log.error("Unable to list triggers", e);
    }
  }

  String recordsToString(final List<TriggerRecord> triggers) {
    if (triggers == null || triggers.isEmpty()) {
      return NO_TRIGGER_RESULT;
    } else {
      final StringBuilder text = new StringBuilder();
      triggers.forEach(record ->
              text.append(record.getTrigger())
                      .append(": ")
                      .append(record.getMessage())
                      .append("\n"));
      return text.toString();
    }
  }

}
