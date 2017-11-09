package ru.ustits.colleague.commands.triggers;

import ru.ustits.colleague.commands.CommandStrategy;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.tools.StringUtils;

/**
 * @author ustits
 */
public interface TriggerStrategy extends CommandStrategy<TriggerRecord> {

  default String parseTrigger(final String[] args) {
    return args[0].toLowerCase();
  }

  default String parseMessage(final String[] args) {
    return StringUtils.asString(args, parametersCount() - 1);
  }
}
