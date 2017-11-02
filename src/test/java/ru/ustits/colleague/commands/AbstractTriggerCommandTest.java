package ru.ustits.colleague.commands;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import ru.ustits.colleague.repositories.TriggerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.anInt;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
@Log4j2
public class AbstractTriggerCommandTest {


  private AbstractTriggerCommand command;

  @Before
  public void setUp() throws Exception {
    command = mockCommand();
  }

  @Test
  public void testResolveTrigger() throws Exception {
    final String trigger = string();
    final String[] args = new String[]{trigger, string()};
    assertThat(command.resolveTrigger(args)).isEqualTo(trigger.toLowerCase());
  }

  private AbstractTriggerCommand mockCommand() {
    return new AbstractTriggerCommand(string(), string(), mock(TriggerRepository.class), anInt()) {
      @Override
      protected void executeInternal(final AbsSender absSender, final User user, final Chat chat,
                                     final String[] arguments) {
        log.info("Executing");
      }
    };
  }


}