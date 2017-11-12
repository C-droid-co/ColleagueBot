package ru.ustits.colleague.commands.repeats;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class DailyParserTest {

  @Test
  public void testTransformCron() throws Exception {
    final DailyParser command = new DailyParser();
    final String cron = "14 20";
    final String result = command.transformCron(cron);
    assertThat(result).isEqualTo("0 20 14 ? * *");
  }

}