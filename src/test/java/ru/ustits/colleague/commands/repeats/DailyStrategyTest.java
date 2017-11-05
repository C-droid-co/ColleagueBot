package ru.ustits.colleague.commands.repeats;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class DailyStrategyTest {

  @Test
  public void testTransformCron() throws Exception {
    final DailyStrategy command = new DailyStrategy();
    final String cron = "14 20";
    final String result = command.transformCron(cron);
    assertThat(result).isEqualTo("* 20 14 * ? *");
  }

}