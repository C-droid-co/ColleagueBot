package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.ustits.colleague.configs.AppConfig.MAX_MESSAGE_LENGTH;

/**
 * @author ustits
 */
@Log4j2
public final class ListTriggersCommand extends BotCommand {

  static final String NO_TRIGGER_RESULT = "No triggers for current chat";

  private final TriggerRepository repository;

  public ListTriggersCommand(final String commandIdentifier, final TriggerRepository repository) {
    super(commandIdentifier, "list triggers");
    this.repository = repository;
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final Long chatId = chat.getId();
    final List<TriggerRecord> triggers = repository.findAllByChatId(chatId);
    final List<String> messages = toMessages(triggers);
    for (final String messageText : messages) {
      final SendMessage message = new SendMessage(chatId, messageText).enableMarkdown(true);
      try {
        absSender.execute(message);
      } catch (TelegramApiException e) {
        log.error("Unable to list triggers", e);
      }
    }
  }

  List<String> toMessages(final List<TriggerRecord> triggers) {
    if (triggers == null || triggers.isEmpty()) {
      return Collections.singletonList(NO_TRIGGER_RESULT);
    } else {
      final List<String> messages = new ArrayList<>();
      StringBuilder builder = new StringBuilder();
      builder.append("*Current triggers:*\n");
      for (final TriggerRecord trigger : triggers) {
        final String text = toMessage(trigger);
        if (builder.length() + text.length() > MAX_MESSAGE_LENGTH) {
          messages.add(builder.toString());
          builder = new StringBuilder();
        }
        builder.append(text);
      }
      messages.add(builder.toString());
      return messages;
    }
  }

  private String toMessage(final TriggerRecord trigger) {
    return String.format("`%s: %s`%n", trigger.getTrigger(), trigger.getMessage());
  }

}
