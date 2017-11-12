package ru.ustits.colleague.commands.repeats;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class WorkDaysParserTest {

  @Test
  public void testTransformCron() throws Exception {
    final RepeatParser strategy = new WorkDaysParser();
    final String cron = "14 20";
    final String result = strategy.transformCron(cron);
    assertThat(result).isEqualTo("0 20 14 ? * 2-6");
  }
}