package ru.ustits.colleague.repositories.records;

import lombok.Value;

/**
 * @author ustits
 */
@Value
public class ChatRecord {

  Long id;
  String type;
  String title;
}
