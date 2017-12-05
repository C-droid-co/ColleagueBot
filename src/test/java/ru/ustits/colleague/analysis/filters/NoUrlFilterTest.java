package ru.ustits.colleague.analysis.filters;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class NoUrlFilterTest {

  private NoUrlFilter filter;

  @Before
  public void setUp() throws Exception {
    filter = new NoUrlFilter();
  }

  @Test
  public void testFilter() {
    final String url = "https://www.google.ru/";
    assertThat(filter.test(url)).isFalse();
  }

  @Test
  public void testFilterNot() {
    final String notUrl = string();
    assertThat(filter.test(notUrl)).isTrue();
  }

}