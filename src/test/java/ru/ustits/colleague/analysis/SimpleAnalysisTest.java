package ru.ustits.colleague.analysis;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.anInt;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class SimpleAnalysisTest {

  private final int TEST_STATS_LEN = 3;

  private SimpleAnalysis analysis;

  @Before
  public void setUp() throws Exception {
    analysis = new SimpleAnalysis(TEST_STATS_LEN);
  }

  @Test
  public void testMostCommonWithStopWords() {
    final String stopWord = string();
    final List<String> words = asList(stopWord, stopWord, string());
    final List<String> stopWords = singletonList(stopWord);
    final Map<String, Integer> stats = analysis.mostCommonWords(words, stopWords);
    assertThat(stats).hasSize(1);
  }

  @Test
  public void testCount() {
    final String first = string();
    final String second = string();
    final String third = string();
    final Map<String, Integer> stats = analysis.count(
            asList(first, second, third, first, third, first));
    assertThat(stats)
            .containsEntry(first, 3)
            .containsEntry(second, 1)
            .containsEntry(third, 2);
    System.out.println(stats);
  }

  @Test
  public void testCountEmptyList() {
    assertThat(analysis.count(emptyList())).isEmpty();
  }

  @Test
  public void testLimit() {
    final Map<String, Integer> data = new HashMap<>();
    final int initialSize = TEST_STATS_LEN * 2;
    for (int i = 0; i < initialSize; i++) {
      data.put(string(), anInt());
    }
    assertThat(data).hasSize(initialSize);
    assertThat(analysis.limit(data, TEST_STATS_LEN)).hasSize(TEST_STATS_LEN);
  }

  @Test
  public void testLimitWithSmallMap() {
    final Map<String, Integer> data = singletonMap(string(), anInt());
    assertThat(analysis.limit(data, TEST_STATS_LEN)).hasSize(data.size());
  }

  @Test
  public void testLimitWithEmptyMap() {
    assertThat(analysis.limit(emptyMap(), TEST_STATS_LEN)).isEmpty();
  }

}