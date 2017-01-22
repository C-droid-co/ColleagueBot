package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author ustits
 */
public class TriggerCommandTest {

  private final TriggerCommand command = new TriggerCommand("random");

  @Mock
  private Chat chat;
  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFailCommandWithFewArguments() {
    final String[] arguments = new String[]{"/trigger"};
    final SendMessage message = command.createRecord(user, chat, arguments);
    assertThat(message.getText(), is(command.failResult()));
  }

  @Test
  public void testFailCommandWithNullArguments() {
    final String[] arguments = null;
    final SendMessage message = command.createRecord(user, chat, arguments);
    assertThat(message.getText(), is(command.failResult()));
  }

  @Test
  public void testCorrectResponseStringBuilding() {
    final String cmd = "command";
    final String first = "first";
    final String last = "last";
    final String expected = String.format("%s %s", first, last);
    final String[] array = new String[] {cmd, first, last};
    final String result = command.resolveMessage(array);
    assertThat(result, is(expected));
  }
}