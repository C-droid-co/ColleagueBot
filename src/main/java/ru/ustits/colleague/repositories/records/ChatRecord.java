package ru.ustits.colleague.repositories.records;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ustits
 */
@Entity
@Table(name = "chats")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class ChatRecord {

  @Id
  private Long id;

  private String type;

  private String title;

  public ChatRecord(final Long id, final String title) {
    this(id, null, title);
  }

}
