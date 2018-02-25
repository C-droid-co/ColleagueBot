package ru.ustits.colleague.commands.triggers;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Chat;
import ru.ustits.colleague.ColleagueBot;
import ru.ustits.colleague.repositories.services.ChatService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.string;
import static ru.ustits.colleague.tools.triggers.ProcessState.ALL;

/**
 * @author ustits
 */
public class ProcessStateCommandTest {

  private ProcessStateCommand command;

  @Before
  public void setUp() throws Exception {
    command = new ProcessStateCommand(string(), string(), mock(ColleagueBot.class), mock(ChatService.class));
  }

  @Test
  public void testChangeState() throws Exception {
    assertThat(command.changeState(new Chat(), ALL.getName())).isTrue();
  }

  @Test
  public void testChangeStateWithWrongState() throws Exception {
    assertThat(command.changeState(new Chat(), string())).isFalse();
  }

}