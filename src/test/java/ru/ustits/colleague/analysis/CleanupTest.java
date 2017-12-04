package ru.ustits.colleague.analysis;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class CleanupTest {

  private Cleanup cleanup;

  @Before
  public void setUp() throws Exception {
    cleanup = new Cleanup(emptyList(), emptyList(), emptyList());
  }

  @Test
  public void testClean() {
    final String stopWord = string();
    final String notStopWord = string();
    final List<String> words = asList(stopWord, stopWord, notStopWord);
    final List<String> stopWords = new ArrayList<>(singletonList(stopWord));
    final List<String> cleaned = cleanup.clean(words, stopWords);
    assertThat(cleaned).containsOnly(notStopWord);
  }

}