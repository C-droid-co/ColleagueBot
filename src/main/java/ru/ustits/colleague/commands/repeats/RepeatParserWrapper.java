package ru.ustits.colleague.commands.repeats;

import lombok.RequiredArgsConstructor;

/**
 * @author ustits
 */
@RequiredArgsConstructor
public abstract class RepeatParserWrapper extends RepeatParser {

  private final RepeatParser innerStrategy;

  @Override
  public String transformCron(final String cron) {
    return innerStrategy.transformCron(cron);
  }

  @Override
  public final int parametersCount() {
    return innerStrategy.parametersCount();
  }
}
