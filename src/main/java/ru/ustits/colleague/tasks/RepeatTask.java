package ru.ustits.colleague.tasks;

import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.commands.RepeatCommand;

/**
 * @author ustits
 */
@Log4j2
public class RepeatTask implements Job {

  @Override
  public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
    final JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
    final AbsSender sender = (AbsSender) data.get(RepeatCommand.SENDER_KEY);
    final String message = data.getString(RepeatCommand.MESSAGE_KEY);
    final Long chatId = data.getLong(RepeatCommand.CHAT_KEY);
    try {
      sender.execute(new SendMessage(chatId, message));
      log.info("Repeated: {} in chat: {}", message, chatId);
    } catch (TelegramApiException e) {
      log.error("Unable to send repeat message", e);
    }
  }
}
