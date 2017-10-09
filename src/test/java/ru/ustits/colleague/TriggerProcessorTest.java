package ru.ustits.colleague;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.ustits.colleague.repositories.TriggerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author ustits
 */
public class TriggerProcessorTest {

  private TriggerProcessor processor;

  @Before
  public void setUp() throws Exception {
    processor = new TriggerProcessor(mock(TriggerRepository.class));
  }

  @Test
  public void testHasTrigger() throws Exception {
    final String text = "SOME text";
    final String trigger = "some";
    final boolean result = processor.hasTrigger(text, trigger);
    assertThat(result).isTrue();
  }

  @Test
  public void testHasNoTrigger() throws Exception {
    final String text = "somesome";
    final String trigger = "some";
    final boolean result = processor.hasTrigger(text, trigger);
    assertThat(result).isFalse();
  }

  @Test
  public void testCreateMessage() throws Exception {
    final String text = "text";
    final SendMessage message = processor.createMessage(text);
    assertThat(message.getText()).isEqualTo(text);
  }
}