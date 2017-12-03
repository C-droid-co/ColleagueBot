package ru.ustits.colleague.analysis.filters;

/**
 * @author ustits
 */
public final class TwitterFilter extends MessageFilter {

  private static final String TWITTER_MSG_PREFIX = "Посмотрите, о чем твитнул";

  public TwitterFilter() {
    super(TWITTER_MSG_PREFIX);
  }

}
