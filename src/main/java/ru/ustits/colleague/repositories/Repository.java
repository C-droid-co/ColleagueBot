package ru.ustits.colleague.repositories;

import org.telegram.telegrambots.api.objects.Update;

/**
 * @author ustits
 */
public interface Repository {

  void add(final Update update);
}
