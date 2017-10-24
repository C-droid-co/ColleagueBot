package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import ru.ustits.colleague.tools.StringUtils;

import static io.qala.datagen.RandomShortApi.alphanumeric;
import static io.qala.datagen.RandomValue.length;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author ustits
 */
public class TriggerCommandTest {

  private static final int RANDOM_BOUND = 10;

  private final TriggerCommand command = new TriggerCommand(alphanumeric(RANDOM_BOUND));

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
  public void testResolveMessage() {
    final String[] args = length(RANDOM_BOUND).alphanumerics().toArray(new String[]{});
    final String result = command.resolveMessage(args);
    assertThat(result, is(StringUtils.asString(args, 1)));
  }

  @Test
  public void testResolveTrigger() throws Exception {
    final String trigger = alphanumeric(RANDOM_BOUND);
    final String[] args = new String[]{trigger, alphanumeric(RANDOM_BOUND)};
    assertThat(command.resolveTrigger(args)).isEqualTo(trigger.toLowerCase());
  }
}