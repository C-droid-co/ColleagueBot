package ru.ustits.colleague;

import org.jooq.DSLContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.ustits.colleague.tables.Triggers;
import ru.ustits.colleague.tables.records.TriggersRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ustits
 */
public class TriggerProcessor {

  private final DSLContext dsl;

  public TriggerProcessor(final DSLContext dsl) {
    this.dsl = dsl;
  }

  public List<SendMessage> process(final Update update) {
    final List<SendMessage> messages = new ArrayList<>();
    final String text = update.getMessage().getText();

    for (final TriggersRecord record : dsl.fetch(Triggers.TRIGGERS)) {
      final String trigger = record.getTrigger();
      if (matches(text, trigger)) {
        messages.add(createMessage(record.getMessage()));
      }
    }
    return messages;
  }

  private boolean matches(final String text, final String regex) {
    final Pattern pattern = Pattern.compile(Pattern.quote(regex),
            Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
    final Matcher matcher = pattern.matcher(text);
    return matcher.find();
  }

  private SendMessage createMessage(final String response) {
    final SendMessage message = new SendMessage();
    message.setText(response);
    return message;
  }
}
