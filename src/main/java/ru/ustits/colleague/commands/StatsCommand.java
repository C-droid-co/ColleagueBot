package ru.ustits.colleague.commands;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.ustits.colleague.repositories.MessageRepository;
import ru.ustits.colleague.repositories.UserRepository;
import ru.ustits.colleague.repositories.records.MessageRecord;
import ru.ustits.colleague.repositories.records.UserRecord;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author ustits
 */
@Log4j2
public class StatsCommand extends BotCommand {

  static final String NO_STAT_MESSAGE = "No statistic yet";

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private UserRepository userRepository;

  public StatsCommand(final String commandIdentifier) {
    super(commandIdentifier, "show message statistics");
  }

  @Override
  public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
    final List<MessageRecord> messages = messageRepository.fetchAll(chat.getId());
    final List<UserRecord> users = userRepository.fetchAll();
    final Map<String, Long> stats = buildStats(messages, users);
    final SendMessage message = new SendMessage(chat.getId(), buildText(stats));
    try {
      absSender.execute(message);
    } catch (TelegramApiException e) {
      log.error("Unable to send stats", e);
    }
  }

  Map<String, Long> buildStats(@NonNull final List<MessageRecord> messages, @NonNull final List<UserRecord> users) {
    if (messages.isEmpty() || users.isEmpty()) {
      return Collections.emptyMap();
    }
    return messages.stream()
            .filter(messageRecord -> !messageRecord.getIsEdited())
            .collect(groupingBy(o -> {
              for (final UserRecord user : users) {
                if (user.getId().equals(o.getUserId())) {
                  return user.getFirstName();
                }
              }
              throw new IllegalStateException("Messages must be mapped to users in db");
            }, counting()));
  }

  String buildText(@NonNull final Map<String, Long> stats) {
    if (stats.isEmpty()) {
      return NO_STAT_MESSAGE;
    }
    final StringBuilder builder = new StringBuilder();
    stats.forEach((userName, count) ->
            builder.append(userName)
                    .append(": ")
                    .append(count)
                    .append("\n"));
    return builder.toString();
  }

}
