package ru.ustits.colleague.repositories.records;

import lombok.Value;

/**
 * @author ustits
 */
@Value
public class TriggerRecord {

  Integer id;
  String trigger;
  String message;
  Long chatId;
  Long userId;
}
