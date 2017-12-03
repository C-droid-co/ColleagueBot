package ru.ustits.colleague.analysis.mappers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ReplaceSymbolsTest {

  @Test
  public void testApply() {
    final ReplaceSymbols mapper = new ReplaceSymbols();
    final String expected = string();
    final String text = "\\\"" + expected + "\"";
    assertThat(mapper.apply(text)).isEqualTo(expected);
  }

}