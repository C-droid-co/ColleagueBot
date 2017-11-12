package ru.ustits.colleague.commands.triggers;

import ru.ustits.colleague.commands.AbstractParser;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public abstract class TriggerParser extends AbstractParser<TriggerRecord> {

  public TriggerParser(final int parametersCount) {
    super(parametersCount);
  }

  protected final String parseTrigger(final String[] args) {
    final Optional<String> trigger = parseString(args, 0);
    return trigger.map(String::toLowerCase).orElse(null);
  }

  protected final String parseMessage(final String[] args) {
    final Optional<String> line = parseString(args, parametersCount() - 1, args.length);
    return line.orElse(null);
  }
}
