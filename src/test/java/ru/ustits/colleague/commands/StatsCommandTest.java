package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.sql.Timestamp;
import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author ustits
 */
public class StatsCommandTest {

  private StatsCommand command;
  private List<MessageRecord> messages;
  private List<UserRecord> users;

  @Before
  public void setUp() throws Exception {
    command = new StatsCommand("random");
    messages = new ArrayList<>();
    users = new ArrayList<>();
  }

  @Test
  public void testBuildStats() throws Exception {
    final String firstName = "name";
    final Long userId = 42L;
    addUser(userId, firstName);
    final Long numberOfMessages = generateMessages(userId);
    final Map<String, Long> result = command.buildStats(messages, users);
    assertThat(result).containsEntry(firstName, numberOfMessages);
  }

  @Test
  public void testStatsIsReversedOrdered() throws Exception {
    final Long lucky = 42L;
    final Long notLucky = 13L;
    addUser(notLucky, "not lucky");
    final Long notLuckyResult = generateMessages(notLucky);
    addUser(lucky, "lucky");
    final Long luckyResult = generateMessages(lucky);
    final Map<String, Long> result = command.buildStats(messages, users);
    if (luckyResult > notLuckyResult) {
      assertThat(result.values()).containsExactly(luckyResult, notLuckyResult);
    } else {
      assertThat(result.values()).containsExactly(notLuckyResult, luckyResult);
    }
  }

  @Test
  public void testBuildStatsWithEditedMessages() throws Exception {
    final String firstName = "name";
    final Long userId = 42L;
    addUser(userId, firstName);
    addMessage("text", userId);
    addEditedMessage("text", userId);
    addEditedMessage("text", userId);
    final Map<String, Long> result = command.buildStats(messages, users);
    assertThat(result).containsEntry(firstName, 1L);
  }

  @Test
  public void testBuildStatsIfMessageHasNoUser() throws Exception {
    final Long userId = 42L;
    addUser(userId, "name");
    addMessage("text", userId + 10);
    assertThatThrownBy(() -> command.buildStats(messages, users))
            .isInstanceOf(IllegalStateException.class);
  }

  @Test
  public void testBuildStatsWithNullMessages() throws Exception {
    assertThatThrownBy(() -> command.buildStats(null, emptyList()))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void testBuildStatsWithNullUsers() throws Exception {
    assertThatThrownBy(() -> command.buildStats(emptyList(), null))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void testBuildStatsWithEmptyMessages() throws Exception {
    final Map<String, Long> result = command.buildStats(emptyList(), users);
    assertThat(result).isEmpty();
  }

  @Test
  public void testBuildStatsWithEmptyUsers() throws Exception {
    final Map<String, Long> result = command.buildStats(messages, emptyList());
    assertThat(result).isEmpty();
  }

  @Test
  public void testBuildText() throws Exception {
    final Map<String, Long> stats = new HashMap<>();
    final String firstUser = "Vanya";
    final Long firstCount = 10L;
    final String secondUser = "Petya";
    final Long secondCount = 20L;
    stats.put(firstUser, firstCount);
    stats.put(secondUser, secondCount);
    final String result = command.buildText(stats);
    assertThat(result)
            .contains(format("%s: %d", firstUser, firstCount))
            .contains(format("%s: %d", secondUser, secondCount));
  }

  @Test
  public void testBuildTextWithEmptyMap() throws Exception {
    final String result = command.buildText(Collections.emptyMap());
    assertThat(result).isEqualTo(StatsCommand.NO_STAT_MESSAGE);
  }

  @Test
  public void testBuildTextWithNull() throws Exception {
    assertThatThrownBy(() -> command.buildText(null))
            .isInstanceOf(IllegalArgumentException.class);
  }

  private void addMessage(final String text, final Long userId) {
    final MessageRecord record = new MessageRecord(1, 1L, new Timestamp(10L),
            text, false, 1L, userId);
    messages.add(record);
  }

  private void addEditedMessage(final String text, final Long userId) {
    final MessageRecord record = new MessageRecord(1, 1L, new Timestamp(10L),
            text, true, 1L, userId);
    messages.add(record);
  }

  private void addUser(final Long userId, final String firstName) {
    final UserRecord record = new UserRecord(userId, firstName, "", "userName");
    users.add(record);
  }

  private Long generateMessages(final Long userId) {
    final long rounds = new Random().nextInt(50) + 1;
    for (int i = 0; i < rounds; i++) {
      addMessage("text", userId);
    }
    return rounds;
  }

}