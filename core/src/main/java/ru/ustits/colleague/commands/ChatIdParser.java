package ru.ustits.colleague.commands;

import java.util.Optional;

/**
 * @author ustits
 */
public final class ChatIdParser<T> extends ParserWrapper<T> {

  private static final int DEFAULT_CHAT_ID_POSITION = 0;

  private final int chatIdPosition;

  public ChatIdParser(final Parser<T> innerParser) {
    this(innerParser, DEFAULT_CHAT_ID_POSITION);
  }

  public ChatIdParser(final Parser<T> innerParser, final int chatIdPosition) {
    super(innerParser);
    this.chatIdPosition = chatIdPosition;
  }

  @Override
  public T buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<Long> parsedChatId = parseLong(arguments, chatIdPosition);
    if (parsedChatId.isPresent()) {
      return super.buildRecord(userId, parsedChatId.get(), arguments);
    } else {
      return null;
    }
  }
}
