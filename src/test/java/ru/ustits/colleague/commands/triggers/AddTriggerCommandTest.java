package ru.ustits.colleague.commands.triggers;

import org.junit.Test;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.tools.StringUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.string;
import static ru.ustits.colleague.RandomUtils.values;

/**
 * @author ustits
 */
public class AddTriggerCommandTest {

  private final AddTriggerCommand command = new AddTriggerCommand(string(), mock(TriggerRepository.class));

  @Test
  public void testResolveMessage() {
    final String[] args = values();
    final String result = command.resolveMessage(args);
    assertThat(result, is(StringUtils.asString(args, 1)));
  }

}