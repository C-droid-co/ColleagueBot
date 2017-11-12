package ru.ustits.colleague.commands.triggers.add;

import ru.ustits.colleague.commands.triggers.TriggerParser;
import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
public class UserParser extends TriggerParser {

  public UserParser(final int parametersCount) {
    super(parametersCount);
  }

  public UserParser() {
    this(2);
  }

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final String trigger = parseTrigger(arguments);
    final String message = parseMessage(arguments);
    return new TriggerRecord(trigger, message, chatId, userId);
  }

}
