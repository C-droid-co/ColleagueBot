package ru.ustits.colleague.repeats.tools;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.CronExpression;
import ru.ustits.colleague.tools.StringUtils;

import java.text.ParseException;
import java.util.Optional;

import static ru.ustits.colleague.repeats.tools.CronFields.MINUTES;
import static ru.ustits.colleague.repeats.tools.CronFields.SECONDS;

/**
 * @author ustits
 */
@Log4j2
@RequiredArgsConstructor
public final class CronRestriction {

  private final CronExpression cron;

  public Optional<CronExpression> restrictToHours() {
    return restrict(SECONDS, MINUTES);
  }

  public Optional<CronExpression> restrictToMinutes() {
    return restrict(SECONDS);
  }

  public Optional<CronExpression> restrict(final CronFields... fields) {
    final String expression = cron.getCronExpression();
    final String[] args = StringUtils.split(expression);
    for (final CronFields field : fields) {
      args[field.getFieldNumber()] = StringUtils.ZERO;

    }
    try {
      final CronExpression newCron = new CronExpression(StringUtils.asString(args));
      return Optional.of(newCron);
    } catch (ParseException e) {
      log.error("Unable to restrict cron", e);
      return Optional.empty();
    }
  }

}
