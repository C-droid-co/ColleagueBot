package ru.ustits.colleague.repositories.records;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author ustits
 */
@Entity
@Table(name = "chats")
@Value
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE, force = true)
public final class ChatRecord {

  @Id
  private Long id;

  private String type;

  private String title;

  @OneToOne
  @JoinColumn(name = "state_id")
  private ChatStateRecord state;

  public ChatRecord(final Long id, final String title) {
    this(id, null, title, null);
  }

  public ChatRecord(final ChatRecord chatRecord, final ChatStateRecord state) {
    this(chatRecord.id, chatRecord.type, chatRecord.title, state);
  }

  public ChatRecord(final ChatStateRecord state) {
    this(null, null, null, state);
  }

}
