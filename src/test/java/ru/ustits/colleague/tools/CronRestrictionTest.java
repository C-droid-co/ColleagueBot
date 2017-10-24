package ru.ustits.colleague.tools;

import org.junit.Test;
import org.quartz.CronExpression;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class CronRestrictionTest {

  @Test
  public void testRestrictToHours() throws Exception {
    final CronExpression cron = new CronExpression("* * 10 ? * *");
    final CronRestriction restriction = new CronRestriction(cron);
    final Optional<CronExpression> result = restriction.restrictToHours();
    assertThat(result).isPresent();
    final CronExpression newExpression = result.get();
    assertThat(newExpression.getCronExpression()).isEqualTo("0 0 10 ? * *");
  }
}