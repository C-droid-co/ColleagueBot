package ru.ustits.colleague.repeats.commands;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class WeekendsParserTest {

  @Test
  public void testTransformCron() throws Exception {
    final RepeatParser strategy = new WeekendsParser();
    final String cron = "14 20";
    final String result = strategy.transformCron(cron);
    assertThat(result).isEqualTo("0 20 14 ? * 1,7");
  }
}