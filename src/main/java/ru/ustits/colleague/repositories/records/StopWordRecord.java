package ru.ustits.colleague.repositories.records;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ustits
 */
@Getter
@ToString
@EqualsAndHashCode(exclude = "id")
public final class StopWordRecord {

  private Integer id;
  private String word;

  public StopWordRecord(final String word) {
    this(null, word);
  }

  public StopWordRecord(final Integer id, final String word) {
    this.id = id;
    this.word = word;
  }

}
