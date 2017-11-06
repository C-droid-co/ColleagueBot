package ru.ustits.colleague.repositories.records;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author ustits
 */
@Getter
@ToString
@EqualsAndHashCode
public final class RepeatRecord {

  private final Integer id;
  private final String message;
  private final Long chatId;
  private final Long userId;
  private final String cron;

  public RepeatRecord(final String message, final String cron,
                      final Long chatId, final Long userId) {
    this(null, message, cron, chatId, userId);
  }

  public RepeatRecord(final Integer id,
                      @NonNull final String cron,
                      @NonNull final String message,
                      @NonNull final Long chatId,
                      @NonNull final Long userId) {
    this.id = id;
    this.message = message;
    this.chatId = chatId;
    this.userId = userId;
    this.cron = cron;
  }
}
