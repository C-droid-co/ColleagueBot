package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import ru.ustits.colleague.repositories.records.RepeatRecord;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class RepeatCommandTest {

  private static final String[] GOOD_ARGS = {"0-30", "1", "23", "?", "*", "1-5", "some", "text"};

  private RepeatCommand command;
  @Mock
  private RepeatScheduler scheduler;
  @Mock
  private Chat chat;
  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    final RepeatService service = mock(RepeatService.class);
    when(scheduler.scheduleTask(any(RepeatRecord.class), any(AbsSender.class))).thenReturn(true);
    when(service.addRepeat(anyString(), anyString(),
            any(Chat.class), any(User.class))).thenReturn(mockRecord());
    command = new RepeatCommand("random", scheduler, service);
  }

  @Test
  public void testScheduleTask() throws Exception {
    final boolean result = command.scheduleTask(GOOD_ARGS, chat, user, mock(AbsSender.class));
    assertThat(result).isTrue();
    verify(scheduler).scheduleTask(any(RepeatRecord.class), any(AbsSender.class));
  }

  @Test
  public void testScheduleTaskWithNullArguments() throws Exception {
    final boolean result = command.scheduleTask(null, chat, user, mock(AbsSender.class));
    assertThat(result).isFalse();
  }

  @Test
  public void testScheduleTaskWithNotEnoughArguments() throws Exception {
    final String[] arguments = {"one", "two"};
    final boolean result = command.scheduleTask(arguments, chat, user, mock(AbsSender.class));
    assertThat(result).isFalse();
  }

  @Test
  public void testParseCron() throws Exception {
    final Optional<String> expression = command.parseCron(GOOD_ARGS);
    assertThat(expression).isPresent();
  }

  @Test
  public void testParseMessage() throws Exception {
    final Optional<String> text = command.parseMessage(GOOD_ARGS);
    assertThat(text).isPresent().contains("some text");
  }

  private RepeatRecord mockRecord() {
    return new RepeatRecord(string(), string(), aLong(), aLong());
  }
}