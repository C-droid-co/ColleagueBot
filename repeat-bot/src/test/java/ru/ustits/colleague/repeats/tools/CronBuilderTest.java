package ru.ustits.colleague.repeats.tools;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class CronBuilderTest {

  @Test
  public void testBuilder() throws Exception {
    final String cron = "* * 10 ? * *";
    final String processed = CronBuilder.builder(cron).build();
    assertThat(processed).isEqualTo(cron);
  }

  @Test
  public void testBuilderWithNull() throws Exception {
    assertThatThrownBy(() -> CronBuilder.builder(null))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void testBuilderWithWrongCron() throws Exception {
    assertThatThrownBy(() -> CronBuilder.builder(string()))
            .isInstanceOf(IllegalStateException.class);
  }

  @Test
  public void testBuilderWithFewCronParameters() throws Exception {
    final String cron = "22 22";
    assertThatThrownBy(() -> CronBuilder.builder(cron))
            .isInstanceOf(IllegalStateException.class);
  }
}