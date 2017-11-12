package ru.ustits.colleague.commands.triggers;

import lombok.extern.log4j.Log4j2;
import ru.ustits.colleague.repositories.records.TriggerRecord;

/**
 * @author ustits
 */
@Log4j2
public class MockParser extends TriggerParser {

  public MockParser(final int parametersCount) {
    super(parametersCount);
  }

  @Override
  public TriggerRecord buildRecord(final Long userId, final Long chatId, final String[] arguments) {
    log.info("Do nothing");
    return null;
  }

}
