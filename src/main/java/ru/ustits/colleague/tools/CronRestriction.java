package ru.ustits.colleague.tools;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Optional;

/**
 * @author ustits
 */
@Log4j2
@RequiredArgsConstructor
public final class CronRestriction {

  private final CronExpression cron;

  public Optional<CronExpression> restrictToHours() {
    final String expression = cron.getCronExpression();
    final String[] args = StringUtils.split(expression);
    args[0] = "0";
    args[1] = "0";
    try {
      final CronExpression newCron = new CronExpression(StringUtils.asString(args));
      return Optional.of(newCron);
    } catch (ParseException e) {
      log.error("Unable to restrict cron", e);
      return Optional.empty();
    }
  }
}
