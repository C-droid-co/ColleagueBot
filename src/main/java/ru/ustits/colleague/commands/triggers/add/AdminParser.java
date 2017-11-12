package ru.ustits.colleague.commands.triggers.add;

import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public final class AdminParser extends UserParser {

  private static final int CHAT_ID_POSITION = 1;

  public AdminParser(final int parametersCount) {
    super(parametersCount);
  }

  public AdminParser() {
    this(3);
  }

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<Long> parsedChatId = parseLong(arguments, CHAT_ID_POSITION);
    if (parsedChatId.isPresent()) {
      return super.buildRecord(userId, parsedChatId.get(), arguments);
    }
    return null;
  }

}
