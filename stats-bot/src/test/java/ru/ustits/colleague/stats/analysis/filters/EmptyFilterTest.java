package ru.ustits.colleague.stats.analysis.filters;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class EmptyFilterTest {

  private EmptyFilter filter;

  @Before
  public void setUp() throws Exception {
    filter = new EmptyFilter();
  }

  @Test
  public void testFilter() {
    assertThat(filter.test("")).isFalse();
  }

  @Test
  public void testFilterNot() {
    assertThat(filter.test(string())).isTrue();
  }

}