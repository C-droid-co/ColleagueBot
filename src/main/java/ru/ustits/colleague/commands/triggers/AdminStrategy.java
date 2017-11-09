package ru.ustits.colleague.commands.triggers;

import org.apache.commons.lang3.math.NumberUtils;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Optional;

/**
 * @author ustits
 */
public final class AdminStrategy implements TriggerStrategy {

  private final Long adminId;
  private final UserStrategy innerStrategy;

  public AdminStrategy(final Long adminId) {
    this(adminId, new UserStrategy());
  }

  public AdminStrategy(final Long adminId, final UserStrategy innerStrategy) {
    this.adminId = adminId;
    this.innerStrategy = innerStrategy;
  }

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    final Optional<Long> parsedChatId = parseChatId(arguments);
    if (parsedChatId.isPresent() && userId.equals(adminId)) {
      return innerStrategy.buildRecord(userId, parsedChatId.get(), arguments);
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
