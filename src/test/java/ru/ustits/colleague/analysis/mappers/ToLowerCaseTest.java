package ru.ustits.colleague.analysis.mappers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ToLowerCaseTest {

  @Test
  public void testApply() {
    final ToLowerCase mapper = new ToLowerCase();
    final String text = string().toUpperCase();
    assertThat(mapper.apply(text)).isEqualTo(text.toLowerCase());
  }

}