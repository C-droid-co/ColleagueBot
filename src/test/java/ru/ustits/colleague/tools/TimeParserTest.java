package ru.ustits.colleague.tools;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author ustits
 */
public class TimeParserTest {

  @Test
  public void testParse() throws Exception {
    final String line = "06:11:42";
    final LocalTime time = TimeParser.parse(line);
    assertThat(time).isEqualTo(LocalTime.parse(line));
  }

  @Test
  public void testParseNull() throws Exception {
    assertThatThrownBy(() -> TimeParser.parse((String) null))
            .isInstanceOf(TimeParseException.class);
  }

  @Test
  public void testParseWithWrongNumberOfParameters() throws Exception {
    final String strange = "10:32:41:123:23";
    assertThatThrownBy(() -> TimeParser.parse(strange))
            .isInstanceOf(TimeParseException.class)
            .hasRootCauseExactlyInstanceOf(DateTimeParseException.class);
  }
}