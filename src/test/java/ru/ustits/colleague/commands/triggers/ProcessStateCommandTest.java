package ru.ustits.colleague.commands.triggers;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.ColleagueBot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.string;
import static ru.ustits.colleague.tools.ProcessStates.ALL;

/**
 * @author ustits
 */
public class ProcessStateCommandTest {

  private ProcessStateCommand command;

  @Before
  public void setUp() throws Exception {
    command = new ProcessStateCommand(string(), string(), mock(ColleagueBot.class));
  }

  @Test
  public void testChangeState() throws Exception {
    assertThat(command.changeState(ALL.getName())).isTrue();
  }

  @Test
  public void testChangeStateWithWrongState() throws Exception {
    assertThat(command.changeState(string())).isFalse();
  }
}