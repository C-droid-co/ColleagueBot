package ru.ustits.colleague.analysis;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.ustits.colleague.tools.StringUtils.split;

/**
 * @author ustits
 */
public final class SimpleTokenizer {

  public List<String> tokenize(final List<String> sentences) {
    final List<String> words = new ArrayList<>();
    sentences.forEach(s -> words.addAll(tokenize(s)));
    return words;
  }

  public List<String> tokenize(final String sentence) {
    final String noPunctuation = sentence.replaceAll("[.,]", "");
    return asList(split(noPunctuation));
  }

}
