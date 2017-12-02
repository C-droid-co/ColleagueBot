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
public final class IgnoreTriggerRecord {

  private final Integer id;
  private final Long chatId;
  private final Long userId;

  public IgnoreTriggerRecord(final Long chatId, final Long userId) {
    this(null, chatId, userId);
  }

  public IgnoreTriggerRecord(final Integer id, final Long chatId, final Long userId) {
    this.id = id;
    this.chatId = chatId;
    this.userId = userId;
  }

}
