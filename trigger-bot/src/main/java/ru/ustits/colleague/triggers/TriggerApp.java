package ru.ustits.colleague.triggers;

import ru.ustits.colleague.ColleagueApp;

/**
 * @author ustits
 */
public class TriggerApp {

  public static void main(final String[] args) {
    final ColleagueApp app = new ColleagueApp(TriggerCommandConfig.class, StateCommandConfig.class);
    app.register(TriggerBot.class);
  }

}
