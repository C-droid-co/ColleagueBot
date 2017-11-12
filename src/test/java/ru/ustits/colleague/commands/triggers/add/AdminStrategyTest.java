package ru.ustits.colleague.commands.triggers.add;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class AdminStrategyTest {

  private AdminStrategy strategy;
  private Long chatId;

  @Before
  public void setUp() throws Exception {
    chatId = aLong();
    strategy = new AdminStrategy();
  }

  @Test
  public void testBuildRecord() throws Exception {
    final TriggerRecord record = strategy.buildRecord(aLong(), aLong(), goodArgs());
    assertThat(record.getChatId()).isEqualTo(chatId);
    assertThat(record.getMessage()).isNotEmpty();
    assertThat(record.getTrigger()).isNotEmpty();
  }

  @Test
  public void testParseChatId() throws Exception {
    final Optional<Long> parsedChatId = strategy.parseChatId(goodArgs());
    assertThat(parsedChatId).isPresent().hasValue(chatId);
  }

  @Test
  public void testParseChatIdIfNotNumberPassed() throws Exception {
    final Optional<Long> parsedChatId = strategy.parseChatId(badArgs());
    assertThat(parsedChatId).isNotPresent();
  }

  private String[] goodArgs() {
    return new String[]{ string(), String.valueOf(chatId), string() };
  }

  private String[] badArgs() {
    return new String[]{ string(), string(), string() };
  }
}