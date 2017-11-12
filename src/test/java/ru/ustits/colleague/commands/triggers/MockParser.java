package ru.ustits.colleague.commands.triggers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
@Log4j2
@RequiredArgsConstructor
public class MockParser implements TriggerParser {

  private final int parametersCount;

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    log.info("Do nothing");
    return null;
  }

  @Override
  public int parametersCount() {
    return parametersCount;
  }
}
