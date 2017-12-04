package ru.ustits.colleague.tools;

import org.junit.Test;

import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ListUtilsTest {

  @Test
  public void testCount() {
    final String first = string();
    final String second = string();
    final String third = string();
    final Map<String, Integer> stats = ListUtils.count(
            asList(first, second, third, first, third, first));
    assertThat(stats)
            .containsEntry(first, 3)
            .containsEntry(second, 1)
            .containsEntry(third, 2);
    System.out.println(stats);
  }

  @Test
  public void testCountEmptyList() {
    assertThat(ListUtils.count(emptyList())).isEmpty();
  }

}