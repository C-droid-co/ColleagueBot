package ru.ustits.colleague.analysis.filters;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class MessageFilterTest {

  private MessageFilter filter;
  private String prefix;

  @Before
  public void setUp() throws Exception {
    prefix = string();
    filter = new MessageFilter(prefix);
  }

  @Test
  public void testFilter() {
    final String text = prefix + " " + string();
    assertThat(filter.test(text)).isFalse();
  }

  @Test
  public void testFilterNot() {
    assertThat(filter.test(string())).isTrue();
  }

}