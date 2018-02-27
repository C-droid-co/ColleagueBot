package ru.ustits.colleague.stats;

import ru.ustits.colleague.ColleagueApp;

/**
 * @author ustits
 */
public class StatsApp {

  public static void main(final String[] args) {
    final ColleagueApp app = new ColleagueApp(StatsConfig.class);
    app.register(StatsBot.class);
  }

}
