package ru.ustits.colleague.repositories.records;

import lombok.Value;

import java.time.LocalTime;

/**
 * @author ustits
 */
@Value
public class RepeatRecord {

  Integer id;
  String message;
  Long chatId;
  Long userId;
  LocalTime time;

}
