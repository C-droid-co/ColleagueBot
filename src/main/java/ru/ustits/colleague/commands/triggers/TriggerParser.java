package ru.ustits.colleague.commands.triggers;

import ru.ustits.colleague.commands.Parser;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.tools.StringUtils;

/**
 * @author ustits
 */
public interface TriggerParser extends Parser<TriggerRecord> {

  default String parseTrigger(final String[] args) {
    return args[0].toLowerCase();
  }

  default String parseMessage(final String[] args) {
    return StringUtils.asString(args, parametersCount() - 1);
  }
}
