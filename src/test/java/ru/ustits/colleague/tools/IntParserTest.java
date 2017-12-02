package ru.ustits.colleague.tools;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.*;

/**
 * @author ustits
 */
public class IntParserTest {

  private IntParser parser;

  @Before
  public void setUp() throws Exception {
    parser = new IntParser();
  }

  @Test
  public void testParse() {
    final Integer number = anInt();
    final Optional<Integer> result = parser.parse(number.toString());
    assertThat(result).isPresent().hasValue(number);
  }

  @Test
  public void testParseNotANumber() {
    assertThat(parser.parse(string())).isNotPresent();
  }

  @Test
  public void testParseNull() {
    assertThat(parser.parse(null)).isNotPresent();
  }

  @Test
  public void testParseEmptyString() {
    assertThat(parser.parse("")).isNotPresent();
  }

  @Test
  public void testParseLongNumber() {
    final Optional<Integer> result = parser.parse(aLong().toString());
    assertThat(result).isNotPresent();
  }

}