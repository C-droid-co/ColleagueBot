package ru.ustits.colleague.tools;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public class TriggerProcessor {

  private final List<TriggerRecord> triggers;

  public List<SendMessage> process(final String text) {
    return triggers.stream().
            filter(record -> hasTrigger(text, record.getTrigger())).
            map(record -> createMessage(record.getMessage())).
            collect(Collectors.toList());
  }

  boolean hasTrigger(final String text, final String trigger) {
    final String regexp = prepareRegexp(removeQuotes(trigger));
    final Pattern pattern = Pattern.compile(regexp,
            Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
    final Matcher matcher = pattern.matcher(text);
    return matcher.find();
  }

  String prepareRegexp(final String text) {
    final String toPattern = Pattern.quote(text);
    return String.format("\\b%s\\b", toPattern);
  }

  String removeQuotes(final String text) {
    return text.replace("\\Q", "")
            .replace("\\E", "");
  }

  SendMessage createMessage(final String response) {
    final SendMessage message = new SendMessage();
    message.setText(response);
    return message;
  }
}
