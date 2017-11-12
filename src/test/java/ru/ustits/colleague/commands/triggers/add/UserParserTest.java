package ru.ustits.colleague.commands.triggers.add;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.valuesMoreThan;

/**
 * @author ustits
 */
public class UserParserTest {

  private UserParser parser;
  private Long chatId;
  private Long userId;
  private String[] args;

  @Before
  public void setUp() throws Exception {
    chatId = aLong();
    userId = aLong();
    parser = new UserParser();
    args = valuesMoreThan(parser.parametersCount());
  }

  @Test
  public void testBuildRecord() throws Exception {
    final TriggerRecord record = parser.buildRecord(userId, chatId, args);
    assertThat(record.getUserId()).isEqualTo(userId);
    assertThat(record.getChatId()).isEqualTo(chatId);
    assertThat(record.getMessage()).isNotEmpty();
    assertThat(record.getTrigger()).isNotEmpty();
  }
}