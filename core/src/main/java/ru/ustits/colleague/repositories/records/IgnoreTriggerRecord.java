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
@Table(name = "ignore_triggers")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class IgnoreTriggerRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "chat_id")
  private Long chatId;

  @Column(name = "user_id")
  private Long userId;

  public IgnoreTriggerRecord(final Long chatId, final Long userId) {
    this(null, chatId, userId);
  }

}
