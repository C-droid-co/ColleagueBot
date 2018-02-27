package ru.ustits.colleague.repeats;

import ru.ustits.colleague.ColleagueApp;

/**
 * @author ustits
 */
public class RepeatApp {

  public static void main(final String[] args) {
    final ColleagueApp app = new ColleagueApp(RepeatCommandConfig.class);
    app.register(RepeatBot.class);
  }

}
