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
public final class UserRecord {

  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String userName;

  public UserRecord(final Long id) {
    this(id, null, null, null);
  }

  public UserRecord(final Long id, final String firstName, final String lastName,
                    final String userName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }
}
