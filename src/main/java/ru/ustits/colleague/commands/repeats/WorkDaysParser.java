package ru.ustits.colleague.commands.repeats;

import ru.ustits.colleague.tools.cron.CronBuilder;

/**
 * @author ustits
 */
public final class WorkDaysParser extends RepeatParserWrapper {

  private static final String WORK_DAYS = "2-6";

  public WorkDaysParser() {
    super(new DailyParser());
  }

  public WorkDaysParser(final DailyParser innerStrategy) {
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
