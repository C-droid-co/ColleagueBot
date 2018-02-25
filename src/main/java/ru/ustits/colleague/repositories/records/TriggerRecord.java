package ru.ustits.colleague.repositories.records;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

/**
 * @author ustits
 */
@Entity
@Table(name = "triggers")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class TriggerRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String trigger;

  private String message;

  @Column(name = "chat_id")
  private Long chatId;

  @Column(name = "user_id")
  private Long userId;

  public TriggerRecord(final String trigger, final Long chatId, final Long userId) {
    this(trigger, null, chatId, userId);
  }

  public TriggerRecord(final String trigger, final String message, final Long chatId,
                       final Long userId) {
    this(null, trigger, message, chatId, userId);
  }

}
