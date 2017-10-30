package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.tools.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.string;
import static ru.ustits.colleague.RandomUtils.values;

/**
 * @author ustits
 */
public class TriggerCommandTest {

  private final TriggerCommand command = new TriggerCommand(string(), mock(TriggerRepository.class));

  @Mock
  private Chat chat;
  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateRecordWithFewArguments() {
    final String[] arguments = new String[]{"/trigger"};
    final SendMessage message = command.createRecord(user, chat, arguments);
    assertThat(message.getText(), is(command.failResult()));
  }

  @Test
  public void testCreateRecordWithNullArguments() {
    final String[] arguments = null;
    final SendMessage message = command.createRecord(user, chat, arguments);
    assertThat(message.getText(), is(command.failResult()));
  }

  @Test
  public void testEnough() throws Exception {
    final String[] args = values();
    assertThat(command.enough(args)).isTrue();
  }

  @Test
  public void testNotEnough() throws Exception {
    final String[] args = values(TriggerCommand.MIN_ARGS - 1);
    assertThat(command.enough(args)).isFalse();
  }

  @Test
  public void testEnoughWithNull() throws Exception {
    assertThat(command.enough(null)).isFalse();
  }

  @Test
  public void testResolveMessage() {
    final String[] args = values();
    final String result = command.resolveMessage(args);
    assertThat(result, is(StringUtils.asString(args, 1)));
  }

  @Test
  public void testResolveTrigger() throws Exception {
    final String trigger = string();
    final String[] args = new String[]{trigger, string()};
    assertThat(command.resolveTrigger(args)).isEqualTo(trigger.toLowerCase());
  }
}