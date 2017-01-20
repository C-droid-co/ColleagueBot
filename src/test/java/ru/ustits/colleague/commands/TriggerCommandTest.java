package ru.ustits.colleague.commands;

import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author ustits
 */
public class TriggerCommandTest {

  private TriggerCommand command = new TriggerCommand("random");

  @Test
  public void testFailCommandWithFewArguments() {
    final String[] arguments = new String[]{"/trigger"};
    final SendMessage message = command.processArgumentsAndSetResponse(arguments);
    assertThat(message.getText(), is(command.failResult()));
  }

  @Test
  public void testFailCommandWithNullArguments() {
    final String[] arguments = null;
    final SendMessage message = command.processArgumentsAndSetResponse(arguments);
    assertThat(message.getText(), is(command.failResult()));
  }

  @Test
  public void testAddTriggerWithEnoughArguments() {
    final String[] arguments = new String[]{"/trigger", "now", "enough"};
    final String trigger = "trigger";
    command = spy(command);
    doReturn(trigger).when(command).addTrigger(arguments);
    command.processArgumentsAndSetResponse(arguments);
    verify(command).addTrigger(arguments);
  }

  @Test
  public void testCorrectResponseStringBuilding() {
    final String cmd = "command";
    final String first = "first";
    final String last = "last";
    final String expected = String.format("%s %s", first, last);
    final String[] array = new String[] {cmd, first, last};
    final String result = command.convertStringArrayToString(array);
    assertThat(result, is(expected));
  }

}