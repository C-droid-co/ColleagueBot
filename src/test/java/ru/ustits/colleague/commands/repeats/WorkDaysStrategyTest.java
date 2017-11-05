package ru.ustits.colleague.commands.repeats;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class WorkDaysStrategyTest {

  @Test
  public void testTransformCron() throws Exception {
    final RepeatStrategy strategy = new WorkDaysStrategy();
    final String cron = "14 20";
    final String result = strategy.transformCron(cron);
    assertThat(result).isEqualTo("* 20 14 ? * 2-6");
  }
}