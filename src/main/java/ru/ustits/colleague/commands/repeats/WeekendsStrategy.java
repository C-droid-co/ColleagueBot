package ru.ustits.colleague.commands.repeats;

import ru.ustits.colleague.tools.CronBuilder;

/**
 * @author ustits
 */
public final class WeekendsStrategy extends RepeatStrategyWrapper {

  private static final String WEEKENDS = "1,7";

  public WeekendsStrategy(final DailyStrategy innerStrategy) {
    super(innerStrategy);
  }

  @Override
  public String transformCron(final String cron) {
    final String processedCron = super.transformCron(cron);
    return CronBuilder.builder(processedCron)
            .withDayOfWeek(WEEKENDS)
            .build();
  }

}
