package ru.ustits.colleague.repositories.records;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author ustits
 */
@Entity
@Table(name = "chat_state")
@Value
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE, force = true)
@ToString(exclude = "chatId")
public class ChatStateRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "chat_id", unique = true, nullable = false)
  private ChatRecord chatId;

  private String state;

  public ChatStateRecord(final ChatRecord chatRecord, final String state) {
    this(null, chatRecord, state);
  }

  public ChatStateRecord(final ChatStateRecord chatStateRecord, final String state) {
    this(chatStateRecord.id, chatStateRecord.chatId, state);
  }

  public ChatStateRecord(final String state) {
    this(null, null, state);
  }

}
