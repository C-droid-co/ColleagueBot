package ru.ustits.colleague.repeats.commands;

/**
 * @author ustits
 */
public abstract class RepeatParserWrapper extends RepeatParser {

  private final RepeatParser innerParser;

  public RepeatParserWrapper(final RepeatParser innerParser) {
    super(innerParser.parametersCount());
    this.innerParser = innerParser;
  }

  @Override
  public String transformCron(final String cron) {
    return innerParser.transformCron(cron);
  }

  @Override
  public final int parametersCount() {
    return innerParser.parametersCount();
  }

  @Override
  protected int getStart() {
    return innerParser.getStart();
  }
}
