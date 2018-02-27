package ru.ustits.colleague.tools.cron;

import org.junit.Before;
import org.junit.Test;
import org.quartz.CronExpression;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class CronRestrictionTest {

  private CronRestriction restriction;

  @Before
  public void setUp() throws Exception {
    final CronExpression cron = new CronExpression("* * 10 ? * *");
    restriction = new CronRestriction(cron);
  }

  @Test
  public void testRestrictToHours() {
    final Optional<CronExpression> result = restriction.restrictToHours();
    assertThat(result).isPresent();
    final CronExpression newExpression = result.get();
    assertThat(newExpression.getCronExpression()).isEqualTo("0 0 10 ? * *");
  }

  @Test
  public void testRestrictToMinutes() {
    final Optional<CronExpression> result = restriction.restrictToMinutes();
    assertThat(result).isPresent();
    final CronExpression newExpression = result.get();
    assertThat(newExpression.getCronExpression()).isEqualTo("0 * 10 ? * *");
  }

  @Test
  public void testRestrict() {
    final Optional<CronExpression> result = restriction.restrict(CronFields.HOURS);
    assertThat(result).isPresent();
    final CronExpression newExpression = result.get();
    assertThat(newExpression.getCronExpression()).isEqualTo("* * 0 ? * *");
  }


}