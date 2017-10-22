package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author ustits
 */
public class RepeatCommandTest {

  private RepeatCommand command;

  @Before
  public void setUp() throws Exception {
    command = new RepeatCommand("random", mock(Scheduler.class));
  }

  @Test
  public void testBuildJob() throws Exception {
    final String text = "text";
    final AbsSender sender = mock(AbsSender.class);
    final JobDetail job = command.buildJob(text, sender);
    assertThat(job.getJobDataMap()).containsValues(text, sender);
  }

  @Test
  public void testParseCron() throws Exception {
    final String[] arguments = {"*", "1", "*", "?", "*", "*", "text"};
    final Optional<CronExpression> expression = command.parseCron(arguments);
    assertThat(expression).isPresent();
  }

  @Test
  public void testScheduleTaskWithNotEnoughArguments() throws Exception {
    final String[] arguments = {"one", "two"};
    final boolean result = command.scheduleTask(arguments, mock(AbsSender.class));
    assertThat(result).isFalse();
  }

  @Test
  public void testParseCronWithNotValidCron() throws Exception {
    final String[] arguments = {"*", "error", "*", "?", "*", "*", "text"};
    final Optional<CronExpression> expression = command.parseCron(arguments);
    assertThat(expression).isNotPresent();
  }
}