package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.commands.repeats.AbstractRepeatCommand;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

/**
 * @author ustits
 */
@Log4j2
public final class RepeatCommand extends AbstractRepeatCommand {

  private static final Integer PARAMETERS_COUNT = 7;

  public RepeatCommand(final String commandIdentifier, final RepeatScheduler scheduler,
                       final RepeatService service) {
    super(commandIdentifier, "command for adding repeatable messages", PARAMETERS_COUNT,
            scheduler, service);
  }

  @Override
  protected String transformCron(final String cron) {
    return cron;
  }
}
