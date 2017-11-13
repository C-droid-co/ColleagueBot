package ru.ustits.colleague.commands.repeats;

import lombok.extern.log4j.Log4j2;

/**
 * @author ustits
 */
@Log4j2
public final class PlainParser extends RepeatParser {

  private static final Integer PARAMETERS_COUNT = 7;

  public PlainParser() {
    super(PARAMETERS_COUNT);
  }

  public PlainParser(final int parametersCount, final int start) {
    super(parametersCount, start);
  }

  @Override
  public String transformCron(final String cron) {
    return cron;
  }

}
