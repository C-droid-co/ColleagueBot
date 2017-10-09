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
  public void testMatches() throws Exception {
    final String text = "SOME text";
    final String pattern = "some";
    final boolean result = processor.matches(text, pattern);
    assertThat(result).isTrue();
  }

  @Test
  public void testCreateMessage() throws Exception {
    final String text = "text";
    final SendMessage message = processor.createMessage(text);
    assertThat(message.getText()).isEqualTo(text);
  }
}