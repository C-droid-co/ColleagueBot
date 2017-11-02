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
public class RepeatDailyTest {

  @Test
  public void testTransformCron() throws Exception {
    final RepeatDaily command = new RepeatDaily(string(), mock(RepeatScheduler.class),
            mock(RepeatService.class));
    final String cron = "14 20";
    final String result = command.transformCron(cron);
    assertThat(result).isEqualTo("* 20 14 * ? *");
  }

}