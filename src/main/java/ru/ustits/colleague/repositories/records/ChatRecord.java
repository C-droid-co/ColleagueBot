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
public final class ChatRecord {

  private final Long id;
  private final String type;
  private final String title;

  public ChatRecord(final String title) {
    this(null, title);
  }

  public ChatRecord(final Long id, final String title) {
    this(id, null, title);
  }

  public ChatRecord(final Long id, final String type, final String title) {
    this.id = id;
    this.type = type;
    this.title = title;
  }
}
