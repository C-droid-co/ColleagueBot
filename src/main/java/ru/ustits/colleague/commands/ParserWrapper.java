package ru.ustits.colleague.commands;

/**
 * @author ustits
 */
public class ParserWrapper<T> extends AbstractParser<T> {

  private final Parser<T> innerParser;

  public ParserWrapper(final Parser<T> innerParser) {
    super(innerParser.parametersCount());
    this.innerParser = innerParser;
  }

  @Override
  public T buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    return innerParser.buildRecord(userId, chatId, arguments);
  }
}
