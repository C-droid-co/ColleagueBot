package ru.ustits.colleague.stats.analysis;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ustits
 */
public class SimpleTokenizerTest {

  private SimpleTokenizer tokenizer;

  @Before
  public void setUp() throws Exception {
    tokenizer = new SimpleTokenizer();
  }

  @Test
  public void testTokenizeSentence() {
    final List<String> tokens = tokenizer.tokenize("The world is indeed comic, but the joke is on mankind.");
    assertThat(tokens).containsOnly(correctValues());
  }

  @Test
  public void testTokenizeSentences() {
    final List<String> sentences = asList("The world is indeed comic", "but the joke is on mankind.");
    final List<String> tokens = tokenizer.tokenize(sentences);
    assertThat(tokens).containsOnly(correctValues());
  }

  private String[] correctValues() {
    return new String[]{"The", "world", "is", "indeed", "comic", "but", "the", "joke", "is", "on", "mankind"};
  }

}