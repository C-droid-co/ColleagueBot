package ru.ustits.colleague.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static ru.ustits.colleague.RandomUtils.*;

/**
 * @author ustits
 */
public class AbstractParserTest {

  private AbstractParser parser;

  @Before
  public void setUp() throws Exception {
    parser = mock(AbstractParser.class, CALLS_REAL_METHODS);
  }

  @Test
  public void testParseString() throws Exception {
    final String[] args = values();
    final int position = aPositiveInt(args.length - 1);
    final Optional<String> result = parser.parseString(args, position);
    assertThat(result).isPresent().hasValue(args[position]);
  }

  @Test
  public void testParseStringWithStartAndStop() throws Exception {
    final String[] args = values();
    final int start = aPositiveInt(args.length - 1);
    final int stop = aPositiveInt(args.length - start) + start;
    final Optional<String> result = parser.parseString(args, start, stop);
    assertThat(result).isPresent();
  }

  @Test
  public void testParseLong() throws Exception {
    final Long number = aLong();
    final String[] args = { String.valueOf(number) };
    assertThat(parser.parseLong(args, 0)).isPresent().hasValue(number);
  }

  @Test
  public void testParseLongIfNotNumberPassed() throws Exception {
    final String[] args = { string() };
    assertThat(parser.parseLong(args, 0)).isNotPresent();
  }

  @Test
  public void testHasElement() throws Exception {
    final String[] args = values();
    final int position = aPositiveInt(args.length - 1);
    assertThat(parser.hasElement(args, position)).isTrue();
  }

  @Test
  public void testHasNoElement() throws Exception {
    final String[] args = values();
    final int position = args.length + 1;
    assertThat(parser.hasElement(args, position)).isFalse();
  }
}