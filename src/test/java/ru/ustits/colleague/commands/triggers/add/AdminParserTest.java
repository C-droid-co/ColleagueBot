package ru.ustits.colleague.commands.triggers.add;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class AdminParserTest {

  private AdminParser parser;
  private Long chatId;

  @Before
  public void setUp() throws Exception {
    chatId = aLong();
    parser = new AdminParser();
  }

  @Test
  public void testBuildRecord() throws Exception {
    final TriggerRecord record = parser.buildRecord(aLong(), aLong(), goodArgs());
    assertThat(record.getChatId()).isEqualTo(chatId);
    assertThat(record.getMessage()).isNotEmpty();
    assertThat(record.getTrigger()).isNotEmpty();
  }


  private String[] goodArgs() {
    return new String[]{ string(), String.valueOf(chatId), string() };
  }

}