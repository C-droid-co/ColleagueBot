package ru.ustits.colleague.repeats.commands;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class PlainParserTest {

  @Test
  public void testTransformCron() throws Exception {
    final PlainParser command = new PlainParser();
    final String cron = string();
    assertThat(command.transformCron(cron)).isEqualTo(cron);
  }
}