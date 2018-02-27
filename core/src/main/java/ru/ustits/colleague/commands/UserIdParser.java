package ru.ustits.colleague.commands;

import java.util.Optional;

/**
 * @author ustits
 */
public final class UserIdParser<T> extends ParserWrapper<T> {

  private static final int DEFAULT_USER_ID_POSITION = 1;

  private final int userIdPosition;

  public UserIdParser(final Parser<T> innerParser) {
    this(innerParser, DEFAULT_USER_ID_POSITION);
  }

  public UserIdParser(final Parser<T> innerParser, final int userIdPosition) {
    super(innerParser);
    this.userIdPosition = userIdPosition;
  }

  @Override
  public T buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<Long> parsedUserId = parseLong(arguments, userIdPosition);
    if (parsedUserId.isPresent()) {
      return super.buildRecord(parsedUserId.get(), chatId, arguments);
    } else {
      return null;
    }
  }
}
