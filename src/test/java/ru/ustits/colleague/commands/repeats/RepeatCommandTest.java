package ru.ustits.colleague.commands.repeats;

import org.junit.Test;
import ru.ustits.colleague.repositories.services.RepeatService;
import ru.ustits.colleague.tasks.RepeatScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class RepeatCommandTest {

  @Test
  public void testTransformCron() throws Exception {
    final RepeatCommand command = new RepeatCommand(string(), mock(RepeatScheduler.class),
            mock(RepeatService.class));
    final String cron = string();
    assertThat(command.transformCron(cron)).isEqualTo(cron);
  }
}