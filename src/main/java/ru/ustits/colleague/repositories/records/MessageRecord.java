package ru.ustits.colleague.repositories.records;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author ustits
 */
@Getter
@ToString
@EqualsAndHashCode
public final class MessageRecord {

  private final Integer id;
  private final Long messageId;
  private final Timestamp date;
  private final String text;
  private final Boolean isEdited;
  private final Long chatId;
  private final Long userId;

  public MessageRecord(final Long messageId, final Timestamp date, final String text,
                       final Boolean isEdited, final Long chatId, final Long userId) {
    this(null, messageId, date, text, isEdited, chatId, userId);
  }

  public MessageRecord(final Integer id, final Long messageId, final Timestamp date,
                       final String text, final Boolean isEdited, final Long chatId,
                       final Long userId) {
    this.id = id;
    this.messageId = messageId;
    this.date = date;
    this.text = text;
    this.isEdited = isEdited;
    this.chatId = chatId;
    this.userId = userId;
  }
}
