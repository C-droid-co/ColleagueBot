package ru.ustits.colleague.commands.repeats;

import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;
import ru.ustits.colleague.tools.CronBuilder;

import static ru.ustits.colleague.tools.StringUtils.split;

/**
 * @author ustits
 */
public final class RepeatDaily extends AbstractRepeatCommand {

  private static final Integer PARAMETERS_COUNT = 3;

  public RepeatDaily(final String commandIdentifier, final RepeatScheduler scheduler,
                     final RepeatService service) {
    super(commandIdentifier, "repeat message every day", PARAMETERS_COUNT,
            scheduler, service);
  }

  @Override
  protected String transformCron(final String cron) {
    final String[] args = split(cron);
    final String hours = args[0];
    final String minutes = args[1];
    return CronBuilder.builder()
            .withHours(hours)
            .withMinutes(minutes)
            .build();
  }
}
