package ru.ustits.colleague.repositories.records;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author ustits
 */
@Builder
@Getter
public final class RepeatRecord {

  private Integer id;
  @NonNull
  private String message;
  @NonNull
  private Long chatId;
  @NonNull
  private Long userId;
  @NonNull
  private String cron;

}
