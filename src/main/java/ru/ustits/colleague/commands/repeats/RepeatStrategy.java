package ru.ustits.colleague.commands.repeats;

/**
 * @author ustits
 */
public interface RepeatStrategy {

  String transformCron(final String cron);

  int parametersCount();
}
