package ru.ustits.colleague.commands;

/**
 * @author ustits
 */
public interface CommandStrategy<T> {

  T buildRecord(final Long userId, final Long chatId, final String[] arguments);

  int parametersCount();
}
