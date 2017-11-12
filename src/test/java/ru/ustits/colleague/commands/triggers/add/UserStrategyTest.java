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
public class UserStrategyTest {

  private UserStrategy strategy;
  private Long chatId;
  private Long userId;
  private String[] args;

  @Before
  public void setUp() throws Exception {
    chatId = aLong();
    userId = aLong();
    strategy = new UserStrategy();
    args = valuesMoreThan(strategy.parametersCount());
  }

  @Test
  public void testBuildRecord() throws Exception {
    final TriggerRecord record = strategy.buildRecord(userId, chatId, args);
    assertThat(record.getUserId()).isEqualTo(userId);
    assertThat(record.getChatId()).isEqualTo(chatId);
    assertThat(record.getMessage()).isNotEmpty();
    assertThat(record.getTrigger()).isNotEmpty();
  }
}