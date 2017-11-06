package ru.ustits.colleague.repositories.records;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ustits
 */
@Getter
@ToString
@EqualsAndHashCode
public final class TriggerRecord {

  private final Integer id;
  private final String trigger;
  private final String message;
  private final Long chatId;
  private final Long userId;

  public TriggerRecord(final String trigger, final Long chatId, final Long userId) {
    this(trigger, null, chatId, userId);
  }

  public TriggerRecord(final String trigger, final String message, final Long chatId,
                       final Long userId) {
    this(null, trigger, message, chatId, userId);
  }

  public TriggerRecord(final Integer id, final String trigger, final String message,
                       final Long chatId, final Long userId) {
    this.id = id;
    this.trigger = trigger;
    this.message = message;
    this.chatId = chatId;
    this.userId = userId;
  }
}
