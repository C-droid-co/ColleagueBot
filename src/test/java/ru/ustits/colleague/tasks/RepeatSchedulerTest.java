package ru.ustits.colleague.tasks;

import org.junit.Before;
import org.junit.Test;
import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static ru.ustits.colleague.tasks.RepeatScheduler.*;

/**
 * @author ustits
 */
public class RepeatSchedulerTest {

  private static final String VALID_CRON = "0-30 1 23 ? * 1-5";

  private RepeatScheduler scheduler;
  private Scheduler quartzScheduler;

  @Before
  public void setUp() throws Exception {
    quartzScheduler = mock(Scheduler.class);
    scheduler = new RepeatScheduler(quartzScheduler);
  }

  @Test
  public void testBuildJob() throws Exception {
    final String text = "text";
    final AbsSender sender = mock(AbsSender.class);
    final Long chatId = 1L;
    final JobDetail job = scheduler.buildJob(text, chatId, sender);
    assertThat(job.getJobDataMap())
            .containsEntry(MESSAGE_KEY, text)
            .containsEntry(SENDER_KEY, sender)
            .containsEntry(CHAT_KEY, chatId);
  }

  @Test
  public void testScheduleTask() throws Exception {
    final boolean result = scheduler.scheduleTask(VALID_CRON, "message",
            1L, mock(AbsSender.class));
    assertThat(result).isTrue();
    verify(quartzScheduler).scheduleJob(any(JobDetail.class), any(Trigger.class));
  }

  @Test
  public void testParseCron() throws Exception {
    final Optional<CronExpression> expression = scheduler.parseCron(VALID_CRON);
    assertThat(expression).isPresent();
  }

  @Test
  public void testParseCronWithNotValidCron() throws Exception {
    final String badCron = "error";
    final Optional<CronExpression> expression = scheduler.parseCron(badCron);
    assertThat(expression).isNotPresent();
  }

}