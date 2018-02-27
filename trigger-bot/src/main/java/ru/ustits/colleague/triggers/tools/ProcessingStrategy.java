package ru.ustits.colleague.triggers.tools;

import java.util.List;

/**
 * @author ustits
 */
public interface ProcessingStrategy {

  List<String> process(final List<String> messages);

}
