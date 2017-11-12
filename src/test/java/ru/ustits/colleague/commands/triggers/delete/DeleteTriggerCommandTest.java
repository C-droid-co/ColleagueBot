package ru.ustits.colleague.commands.triggers.delete;

import org.junit.Before;
import org.junit.Test;
import ru.ustits.colleague.commands.triggers.add.UserParser;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class DeleteTriggerCommandTest {

  private DeleteTriggerCommand command;
  private TriggerRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = mock(TriggerRepository.class);
    command = new DeleteTriggerCommand(string(), repository, new UserParser());
  }

  @Test
  public void testDeleteTrigger() throws Exception {
    when(repository.exists(any(TriggerRecord.class))).thenReturn(true);
    final boolean result = command.deleteTrigger(mockRecord());
    assertThat(result).isTrue();
    verify(repository).exists(any(TriggerRecord.class));
    verify(repository).delete(any(TriggerRecord.class));
  }

  @Test
  public void testDeleteTriggerThatNotExists() throws Exception {
    when(repository.exists(any(TriggerRecord.class))).thenReturn(false);
    final boolean result = command.deleteTrigger(mockRecord());
    assertThat(result).isFalse();
    verify(repository).exists(any(TriggerRecord.class));
    verify(repository, never()).delete(any(TriggerRecord.class));
  }

  @Test
  public void testDeleteTriggerWithNullRecord() throws Exception {
    final boolean result = command.deleteTrigger(null);
    assertThat(result).isFalse();
    verify(repository, never()).exists(any(TriggerRecord.class));
    verify(repository, never()).delete(any(TriggerRecord.class));
  }

  private TriggerRecord mockRecord() {
    return new TriggerRecord(string(), aLong(), aLong());
  }
}