package ru.ustits.colleague.commands.repeats;

import ru.ustits.colleague.tools.CronBuilder;

/**
 * @author ustits
 */
public final class WorkDaysStrategy extends RepeatStrategyWrapper {

  private static final String WORK_DAYS = "2-6";

  public WorkDaysStrategy() {
    super(new DailyStrategy());
  }

  public WorkDaysStrategy(final DailyStrategy innerStrategy) {
    super(innerStrategy);
  }

  @Override
  public String transformCron(final String cron) {
    final String processedCron = super.transformCron(cron);
    return CronBuilder.builder(processedCron)
            .withDayOfWeek(WORK_DAYS)
            .build();
  }

}
