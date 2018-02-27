package ru.ustits.colleague.repositories.records;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ustits
 */
@Entity
@Table(name = "messages")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class MessageRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "msg_id")
  private Long messageId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  private String text;

  @Column(name = "is_edited")
  private Boolean isEdited;

  @Column(name = "chat_id")
  private Long chatId;

  @Column(name = "user_id")
  private Long userId;

  public MessageRecord(final Long messageId, final Date date, final String text,
                       final Boolean isEdited, final Long chatId, final Long userId) {
    this(null, messageId, date, text, isEdited, chatId, userId);
  }

}
