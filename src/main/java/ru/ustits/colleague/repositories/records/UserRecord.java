package ru.ustits.colleague.repositories.records;

import lombok.Value;

/**
 * @author ustits
 */
@Value
public class UserRecord {

  Long id;
  String firstName;
  String lastName;
  String userName;
}
