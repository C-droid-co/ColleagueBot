package ru.ustits.colleague.commands.triggers;

import ru.ustits.colleague.commands.AbstractParser;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public final class TriggerParser extends AbstractParser<TriggerRecord> {

  private static final int DEFAULT_TRIGGER_POSITION = 0;

  private final int triggerPosition;

  public TriggerParser(final int parametersCount) {
    this(parametersCount, DEFAULT_TRIGGER_POSITION);
  }

  public TriggerParser(final int parametersCount, final int triggerPosition) {
    super(parametersCount);
    this.triggerPosition = triggerPosition;
  }

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final String trigger = parseTrigger(arguments);
    final String message = parseMessage(arguments);
    return new TriggerRecord(trigger, message, chatId, userId);
  }

  protected String parseTrigger(final String[] args) {
    final Optional<String> trigger = parseString(args, triggerPosition);
    return trigger.map(String::toLowerCase).orElse(null);
  }

  protected String parseMessage(final String[] args) {
    final Optional<String> line = parseString(args, parametersCount() - 1, args.length);
    return line.orElse(null);
  }
}
