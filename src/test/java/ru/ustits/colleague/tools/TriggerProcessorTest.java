package ru.ustits.colleague.tools;

import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class TriggerProcessorTest {

  private TriggerProcessor processor;
  private String trigger;

  @Before
  public void setUp() throws Exception {
    trigger = "trigger";
    final TriggerRecord record = new TriggerRecord(0, trigger, "hello there!", 1L, 1L);
    processor = new TriggerProcessor(Collections.singletonList(record));
  }

  @Test
  public void testProcess() throws Exception {
    final String text = "some text with trigger";
    final List<SendMessage> messages = processor.process(text);
    assertThat(messages).hasSize(1);
  }

  @Test
  public void testHasTrigger() throws Exception {
    final String text = "TRIGGER text";
    final boolean result = processor.hasTrigger(text, trigger);
    assertThat(result).isTrue();
  }

  @Test
  public void testHasNoTrigger() throws Exception {
    final String text = "triggertrigger";
    final boolean result = processor.hasTrigger(text, trigger);
    assertThat(result).isFalse();
  }

  @Test
  public void testCreateMessage() throws Exception {
    final String text = "text";
    final SendMessage message = processor.createMessage(text);
    assertThat(message.getText()).isEqualTo(text);
  }

  @Test
  public void testPrepareRegexp() throws Exception {
    final String text = string();
    final String regexp = processor.prepareRegexp(text);
    assertThat(Pattern.compile(regexp)).isNotNull();
  }

  @Test
  public void testPrepareRegexpWithBrace() throws Exception {
    final String text = "{";
    final String regexp = processor.prepareRegexp(text);
    assertThat(Pattern.compile(regexp)).isNotNull();
  }
}