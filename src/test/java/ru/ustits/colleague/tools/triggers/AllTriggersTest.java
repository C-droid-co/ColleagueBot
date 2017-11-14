package ru.ustits.colleague.tools.triggers;

import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class AllTriggersTest {

  @Test
  public void testProcess() throws Exception {
    final AllTriggers strategy = new AllTriggers();
    final List<SendMessage> original = new ArrayList<>();
    final List<SendMessage> processed = strategy.process(original);
    assertThat(processed).isEqualTo(original);
  }

}