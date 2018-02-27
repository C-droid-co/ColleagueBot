package ru.ustits.colleague.tools.triggers;

import java.util.List;

/**
 * @author ustits
 */
public interface ProcessingStrategy {

  List<String> process(final List<String> messages);

}
