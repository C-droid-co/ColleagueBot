package ru.ustits.colleague.commands.triggers;

import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
public final class UserStrategy implements TriggerStrategy {

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final String trigger = parseTrigger(arguments);
    final String message = parseMessage(arguments);
    return new TriggerRecord(trigger, message, chatId, userId);
  }

  @Override
  public int parametersCount() {
    return 2;
  }

}
