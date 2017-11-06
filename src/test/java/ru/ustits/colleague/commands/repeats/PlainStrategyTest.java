package ru.ustits.colleague.commands.repeats;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class PlainStrategyTest {

  @Test
  public void testTransformCron() throws Exception {
    final PlainStrategy command = new PlainStrategy();
    final String cron = string();
    assertThat(command.transformCron(cron)).isEqualTo(cron);
  }
}