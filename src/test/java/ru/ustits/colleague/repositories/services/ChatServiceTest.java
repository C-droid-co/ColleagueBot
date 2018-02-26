package ru.ustits.colleague.repositories.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ustits.colleague.repositories.ChatStateRepository;
import ru.ustits.colleague.repositories.ChatsRepository;
import ru.ustits.colleague.repositories.records.ChatRecord;
import ru.ustits.colleague.repositories.records.ChatStateRecord;
import ru.ustits.colleague.tools.triggers.ProcessState;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.ustits.colleague.RandomUtils.aLong;
import static ru.ustits.colleague.RandomUtils.string;

/**
 * @author ustits
 */
public class ChatServiceTest {

  private ChatService service;

  @Mock
  private ChatsRepository chatsRepository;

  @Mock
  private ChatStateRepository chatStateRepository;

  private ProcessState defaultState;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    defaultState = ProcessState.LAST;
    service = new ChatService(chatsRepository, chatStateRepository, defaultState);
  }

  @Test
  public void testGetChatState() {
    final ProcessState processState = ProcessState.ALL;
    final ChatStateRecord state = new ChatStateRecord(processState.getName());
    final ChatRecord value = new ChatRecord(state);
    when(chatsRepository.findById(anyLong())).thenReturn(Optional.of(value));
    final ProcessState result = service.getChatState(aLong());
    assertThat(result).isEqualTo(processState);
    verify(chatsRepository).findById(anyLong());
  }

  @Test
  public void testGetChatStateWithNoFoundChatId() {
    when(chatsRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThat(service.getChatState(aLong())).isEqualTo(defaultState);
    verify(chatsRepository).findById(anyLong());
  }

  @Test
  public void testGetChatStateWithBadStateInDb() {
    final ChatStateRecord state = new ChatStateRecord(string());
    final ChatRecord value = new ChatRecord(state);
    when(chatsRepository.findById(anyLong())).thenReturn(Optional.of(value));
    assertThat(service.getChatState(aLong())).isEqualTo(defaultState);
    verify(chatsRepository).findById(anyLong());
  }

}