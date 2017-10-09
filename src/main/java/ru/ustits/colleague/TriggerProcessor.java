package ru.ustits.colleague;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public class TriggerProcessor {

  private final TriggerRepository repository;

  public List<SendMessage> process(final Update update) {
    final List<SendMessage> messages = new ArrayList<>();
    final String text = update.getMessage().getText();

    final Long chatId = update.getMessage().getChatId();

    for (final TriggerRecord record : repository.fetchAll(chatId)) {
      final String trigger = record.getTrigger();
      if (hasTrigger(text, trigger)) {
        messages.add(createMessage(record.getMessage()));
      }
    }
    return messages;
  }

  boolean hasTrigger(final String text, final String trigger) {
    final String regexp = String.format("\\b%s\\b", trigger);
    final Pattern pattern = Pattern.compile(regexp,
            Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
    final Matcher matcher = pattern.matcher(text);
    return matcher.find();
  }

  SendMessage createMessage(final String response) {
    final SendMessage message = new SendMessage();
    message.setText(response);
    return message;
  }
}
