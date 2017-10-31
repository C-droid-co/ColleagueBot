package ru.ustits.colleague.repositories.records;

import lombok.Value;

import java.sql.Timestamp;

/**
 * @author ustits
 */
@Value
public class MessageRecord {

  Integer id;
  Long messageId;
  Timestamp date;
  String text;
  Boolean isEdited;
  Long chatId;
  Long userId;

}
