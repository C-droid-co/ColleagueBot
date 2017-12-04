package ru.ustits.colleague.analysis;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class SimpleAnalysisTest {

  private SimpleAnalysis analysis;

  @Before
  public void setUp() throws Exception {
    analysis = new SimpleAnalysis(3);
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

}