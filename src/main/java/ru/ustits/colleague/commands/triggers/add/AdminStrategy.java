package ru.ustits.colleague.commands.triggers.add;

import org.apache.commons.lang3.math.NumberUtils;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public final class AdminStrategy extends UserStrategy {

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<Long> parsedChatId = parseChatId(arguments);
    if (parsedChatId.isPresent()) {
      return super.buildRecord(userId, parsedChatId.get(), arguments);
    }
    return null;
  }

  @Override
  public int parametersCount() {
    return 3;
  }

  protected final Optional<Long> parseChatId(final String[] args) {
    final Long chatId = NumberUtils.isParsable(args[1]) ? Long.parseLong(args[1]) : null;
    return Optional.ofNullable(chatId);
  }

}
