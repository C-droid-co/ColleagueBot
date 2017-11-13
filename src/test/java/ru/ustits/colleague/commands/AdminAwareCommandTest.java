package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
@Log4j2
public class AdminAwareCommandTest {

  private AdminAwareCommand command;
  private Long adminId;

  @Before
  public void setUp() throws Exception {
    adminId = aLong();
    command = mockCommand(adminId);
  }

  @Test
  public void testIsAdmin() throws Exception {
    assertThat(command.isAdmin(adminId)).isTrue();
  }

  @Test
  public void testIsNotAdmin() throws Exception {
    assertThat(command.isAdmin(aLong())).isFalse();
  }

  @Test
  public void testIsNotAdminWithNullPassed() throws Exception {
    assertThat(command.isAdmin(null)).isFalse();
  }

  private AdminAwareCommand mockCommand(final Long adminId) {
    final BotCommand command = new BotCommand(string(), string()) {
      @Override
      public void execute(final AbsSender absSender, final User user, final Chat chat, final String[] arguments) {
        log.info("Executing");
      }
    };
    return new AdminAwareCommand(command, adminId);
  }
}