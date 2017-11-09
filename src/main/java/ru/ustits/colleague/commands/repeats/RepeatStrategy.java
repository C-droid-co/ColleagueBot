package ru.ustits.colleague.commands.repeats;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.commands.CommandStrategy;
import ru.ustits.colleague.repositories.records.RepeatRecord;

import java.util.Optional;

import static ru.ustits.colleague.tools.StringUtils.asString;

/**
 * @author ustits
 */
@Log4j2
public abstract class RepeatStrategy implements CommandStrategy<RepeatRecord> {

  public final RepeatRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<String> message = parseMessage(arguments);
    final Optional<String> cron = parseCron(arguments);
    if (message.isPresent() && cron.isPresent()) {
      return new RepeatRecord(message.get(), transformCron(cron.get()), chatId, userId);
    } else {
      log.debug("Unable to create RepeatRecord");
      return null;
    }
  }

  protected abstract String transformCron(final String cron);

  protected final Optional<String> parseMessage(final String[] arguments) {
    final String text = asString(arguments, parametersCount() - 1);
    log.info("Parsed repeat task text: {}", text);
    return Optional.of(text);
  }

  protected final Optional<String> parseCron(final String[] arguments) {
    final String cron = asString(arguments, 0, parametersCount() - 1);
    log.info("Parsed cron: {}", cron);
    return Optional.of(cron);
  }
}
