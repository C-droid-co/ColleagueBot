package ru.ustits.colleague.triggers.tools;

import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author ustits
 */
public class TriggerProcessor {

  private final List<TriggerRecord> triggers;
  private final ProcessingStrategy strategy;

  public TriggerProcessor(final List<TriggerRecord> triggers) {
    this(triggers, new AllTriggers());
  }

  public TriggerProcessor(final List<TriggerRecord> triggers, final ProcessingStrategy strategy) {
    this.triggers = triggers;
    this.strategy = strategy;
  }

  public List<String> process(final String text) {
    return strategy.process(
            triggers.stream().
                    filter(record -> hasTrigger(text, record.getTrigger())).
                    map(TriggerRecord::getMessage).
                    collect(Collectors.toList()));
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

}
