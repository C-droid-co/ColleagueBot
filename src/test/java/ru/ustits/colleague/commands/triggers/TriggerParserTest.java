package ru.ustits.colleague.commands.triggers;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.tools.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.valuesMoreThan;

/**
 * @author ustits
 */
public class TriggerParserTest {

  private int parametersCount;
  private TriggerParser parser;
  private String[] args;

  @Before
  public void setUp() throws Exception {
    parametersCount = 3;
    parser = new TriggerParser(parametersCount);
    args = valuesMoreThan(parametersCount);
  }

  @Test
  public void testParseMessage() throws Exception {
    final String result = parser.parseMessage(args);
    assertThat(result).isEqualTo(StringUtils.asString(args, parametersCount - 1));
  }

  @Test
  public void testParseTrigger() throws Exception {
    final String result = parser.parseTrigger(args);
    assertThat(result).isEqualTo(args[0].toLowerCase());
  }

  @Test
  public void testBuildRecord() throws Exception {
    final Long userId = aLong();
    final Long chatId = aLong();
    final TriggerRecord record = parser.buildRecord(userId, chatId, args);
    assertThat(record.getUserId()).isEqualTo(userId);
    assertThat(record.getChatId()).isEqualTo(chatId);
    assertThat(record.getMessage()).isNotEmpty();
    assertThat(record.getTrigger()).isNotEmpty();
  }
}