package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.telegram.telegrambots.bots.AbsSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author ustits
 */
public class RepeatCommandTest {

  private RepeatCommand command;

  @Before
  public void setUp() throws Exception {
    command = new RepeatCommand("random");
  }

  @Test
  public void testBuildJob() throws Exception {
    final String text = "text";
    final AbsSender sender = mock(AbsSender.class);
    final JobDetail job = command.buildJob(text, sender);
    assertThat(job.getJobDataMap()).containsValues(text, sender);
  }

}