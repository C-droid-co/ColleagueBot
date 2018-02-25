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
@Table(name = "repeats")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class RepeatRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String message;

  private String cron;

  @Column(name = "chat_id")
  private Long chatId;

  @Column(name = "user_id")
  private Long userId;

  public RepeatRecord(final String message, final String cron,
                      final Long chatId, final Long userId) {
    this(null, message, cron, chatId, userId);
  }

}
