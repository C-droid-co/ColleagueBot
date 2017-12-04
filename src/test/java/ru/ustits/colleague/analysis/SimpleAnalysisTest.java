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
    analysis = new SimpleAnalysis(emptyList(), emptyList(), emptyList());
  }

  @Test
  public void testMostCommonWithStopWords() {
    final String stopWord = string();
    final String notStopWord = string();
    final List<String> words = asList(stopWord, stopWord, notStopWord);
    final List<String> stopWords = singletonList(stopWord);
    final Map<String, Integer> stats = analysis.mostCommonWords(words, stopWords);
    assertThat(stats).hasSize(1).containsEntry(notStopWord, 1);
  }

}