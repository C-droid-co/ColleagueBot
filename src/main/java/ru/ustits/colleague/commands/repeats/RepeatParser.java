package ru.ustits.colleague.commands.repeats;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.commands.AbstractParser;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author ustits
 */
@Log4j2
public abstract class RepeatParser extends AbstractParser<RepeatRecord> {

  private static final int DEFAULT_START_INDEX = 0;

  @Getter(PROTECTED)
  private final int start;

  public RepeatParser(final int parametersCount) {
    this(parametersCount, DEFAULT_START_INDEX);
  }

  public RepeatParser(final int parametersCount, final int start) {
    super(parametersCount);
    this.start = start;
  }

  public final RepeatRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<String> message = parseString(arguments, parametersCount() - 1, arguments.length);
    final Optional<String> cron = parseString(arguments, getStart(), parametersCount() - 1);
    if (message.isPresent() && cron.isPresent()) {
      return new RepeatRecord(message.get(), transformCron(cron.get()), chatId, userId);
    } else {
      log.debug("Unable to create RepeatRecord");
      return null;
    }
  }

  protected abstract String transformCron(final String cron);

}
