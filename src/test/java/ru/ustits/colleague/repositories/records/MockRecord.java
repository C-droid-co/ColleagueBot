package ru.ustits.colleague.repositories.records;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static ru.ustits.colleague.RandomUtils.aPositiveInt;

/**
 * @author ustits
 */
@Getter
@ToString
@EqualsAndHashCode
public class MockRecord {

  private static final Integer MAX_ID = Integer.MAX_VALUE;

  private int id;

  public MockRecord() {
    this.id = aPositiveInt(MAX_ID);
  }

}
