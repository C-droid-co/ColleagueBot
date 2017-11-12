package ru.ustits.colleague.commands.repeats;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author ustits
 */
public class RepeatParserTest {

  private RepeatParser strategy;

  @Before
  public void setUp() throws Exception {
    strategy = mock(RepeatParser.class, CALLS_REAL_METHODS);
    doReturn(7).when(strategy).parametersCount();
  }

  @Test
  public void testParseCron() throws Exception {
    final Optional<String> expression = strategy.parseCron(goodArgs());
    assertThat(expression).isPresent().hasValue("0-30 1 23 ? * 1-5");
  }

  @Test
  public void testParseMessage() throws Exception {
    final Optional<String> text = strategy.parseMessage(goodArgs());
    assertThat(text).isPresent().hasValue("some text");
  }

  private String[] goodArgs() {
    return new String[]{"0-30", "1", "23", "?", "*", "1-5", "some", "text"};
  }

}