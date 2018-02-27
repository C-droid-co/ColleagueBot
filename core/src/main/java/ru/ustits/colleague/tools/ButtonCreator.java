package ru.ustits.colleague.tools;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * @author ustits
 */
public class ButtonCreator {

  private ButtonCreator() {}

  public static InlineKeyboardButton create(final String text, final String callBackData) {
    final InlineKeyboardButton button = new InlineKeyboardButton();
    button.setText(text);
    button.setCallbackData(callBackData);
    return button;
  }
}
