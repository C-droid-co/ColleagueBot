package ru.ustits.colleague.repositories.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ustits.colleague.repositories.IgnoreTriggerRepository;
import ru.ustits.colleague.repositories.TriggerRepository;
import ru.ustits.colleague.repositories.records.TriggerRecord;
import ru.ustits.colleague.tools.triggers.ProcessState;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class TriggerServiceTest {

  private TriggerService service;

  @Mock
  private TriggerRepository triggerRepository;

  @Mock
  private IgnoreTriggerRepository ignoreTriggerRepository;

  @Mock
  private ChatService chatService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    service = new TriggerService(triggerRepository, ignoreTriggerRepository, chatService);
  }

  @Test
  public void testFindTriggers() {
    final String trigger = string();
    final String message = string();
    final List<TriggerRecord> triggers = singletonList(new TriggerRecord(trigger, message));
    when(triggerRepository.findAllByChatId(anyLong())).thenReturn(triggers);
    final List<String> result = service.findTriggers(aLong(), trigger, ProcessState.ALL.getStrategy());
    assertThat(result).containsOnly(message);
  }

}