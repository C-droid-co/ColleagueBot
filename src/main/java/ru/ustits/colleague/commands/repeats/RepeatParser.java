package ru.ustits.colleague.commands.repeats;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.commands.AbstractParser;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
public abstract class RepeatParser extends AbstractParser<RepeatRecord> {

  public RepeatParser(final int parametersCount) {
    super(parametersCount);
  }

  public final RepeatRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<String> message = parseString(arguments, parametersCount() - 1, arguments.length);
    final Optional<String> cron = parseString(arguments, 0, parametersCount() - 1);
    if (message.isPresent() && cron.isPresent()) {
      return new RepeatRecord(message.get(), transformCron(cron.get()), chatId, userId);
    } else {
      log.debug("Unable to create RepeatRecord");
      return null;
    }
  }

  protected abstract String transformCron(final String cron);

}
