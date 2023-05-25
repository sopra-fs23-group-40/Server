package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        testLobby = new Lobby();
        testLobby.setLobbyId(1L);
        testLobby.setName("testUsername's Lobby");
        testLobby.setHost("testUsername");
        testLobby.setLobbyType(LobbyType.PRIVATE);
        testLobby.setStatus(LobbyStatus.WAITING);
    }

    @Test
     void createLobby_Successful(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());

        // then
        verify(lobbyRepository, times(1)).save(Mockito.any());

        assertEquals(testLobby.getName(), createdLobby.getName());
        assertEquals(testLobby.getHost(), createdLobby.getHost());
        assertNotNull(createdLobby.getLobbyToken());
        assertEquals(testLobby.getLobbyType(), createdLobby.getLobbyType());
        assertEquals(testLobby.getStatus(), createdLobby.getStatus());
    }

    @Test
     void changeLobbytype_Successful(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        //check the lobby type before
        assertEquals(testLobby.getLobbyType(), createdLobby.getLobbyType());
        createdLobby.setLobbyId(2L);
        given(lobbyRepository.findByHost(createdLobby.getHost())).willReturn(createdLobby);
        given(lobbyRepository.findByLobbyId(createdLobby.getLobbyId())).willReturn(createdLobby);

        //change to public test
        lobbyService.changeLobbytype(createdLobby.getHost(), createdLobby.getLobbyId());
        assertEquals(LobbyType.PUBLIC, createdLobby.getLobbyType());

        //change to private test
        lobbyService.changeLobbytype(createdLobby.getHost(), createdLobby.getLobbyId());
        assertEquals(LobbyType.PRIVATE, createdLobby.getLobbyType());
    }

    @Test
     void changeLobbytype_NotHostAnyLobby_ReturnBadRequest(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> lobbyService.changeLobbytype("wrong", createdLobby.getLobbyId()));
        String expectedMessage = "400 BAD_REQUEST \"The user isn't the host of any lobby!\"";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
     void changeLobbytype_WrongLobby_ReturnBadRequest(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        given(lobbyRepository.findByHost(createdLobby.getHost())).willReturn(createdLobby);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> lobbyService.changeLobbytype(createdLobby.getHost(), 3L));
        String expectedMessage = "400 BAD_REQUEST \"There is no lobby with this id!\"";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
     void deleteLobby_Successful(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        given(lobbyRepository.findByHost(createdLobby.getHost())).willReturn(createdLobby);
        given(lobbyRepository.findByLobbyId(createdLobby.getLobbyId())).willReturn(createdLobby);
        lobbyService.deleteLobby(createdLobby.getHost(), createdLobby.getLobbyId());
        verify(lobbyRepository, times(1)).delete(Mockito.any());
    }

    @Test
     void leaveLobby_Successful(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        createdLobby.setPlayerList("testUsername,deletePlayer");
        createdLobby.setCurrentPlayers(2);
        given(lobbyRepository.findByLobbyId(createdLobby.getLobbyId())).willReturn(createdLobby);

        lobbyService.leaveLobby("deletePlayer", createdLobby.getLobbyId());

        //2 times because of createLobby and leaveLobby
        verify(lobbyRepository, times(2)).save(Mockito.any());
        assertEquals(1, createdLobby.getCurrentPlayers());
        assertEquals("testUsername", createdLobby.getPlayerList());
    }

    @Test
     void leaveLobby_UserNotInLobby_ReturnBadRequest(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        createdLobby.setPlayerList("testUsername,deletePlayer");
        createdLobby.setCurrentPlayers(2);
        given(lobbyRepository.findByLobbyId(createdLobby.getLobbyId())).willReturn(createdLobby);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> lobbyService.leaveLobby("player1", createdLobby.getLobbyId()));
        String expectedMessage = "400 BAD_REQUEST \"There is no player with that username in the lobby!\"";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
     void checkIfHost_Successful(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        given(lobbyRepository.findByLobbyId(createdLobby.getLobbyId())).willReturn(createdLobby);
        assertTrue(lobbyService.checkIfHost("testUsername", createdLobby.getLobbyId()));
    }

    @Test
     void joinLobby_Successful(){
        Lobby createdLobby =  lobbyService.createLobby(testLobby.getHost());
        createdLobby.setLobbyId(2L);
        given(lobbyRepository.findByLobbyId(createdLobby.getLobbyId())).willReturn(createdLobby);

        lobbyService.joinLobby(createdLobby.getLobbyId(), createdLobby.getLobbyToken(), "player1");

        verify(lobbyRepository, times(2)).save(Mockito.any());
        assertEquals(2, createdLobby.getCurrentPlayers());
        assertEquals("testUsername,player1", createdLobby.getPlayerList());
    }

    @Test
    void testLobbySetStatus(){
        Lobby testLobby = new Lobby();
        testLobby.setLobbyId(1L);
        testLobby.setStatus(LobbyStatus.WAITING);

        given(lobbyRepository.findByLobbyId(testLobby.getLobbyId())).willReturn(testLobby);
        lobbyService.setStatus(testLobby.getLobbyId(), LobbyStatus.INGAME);

        assertEquals(LobbyStatus.INGAME, testLobby.getStatus());
        verify(lobbyRepository, times(1)).flush();
    }

    @Test
    void testLobbySetGameId(){
        Lobby testLobby = new Lobby();
        testLobby.setLobbyId(1L);

        given(lobbyRepository.findByLobbyId(testLobby.getLobbyId())).willReturn(testLobby);
        lobbyService.setGameId(testLobby.getLobbyId(), String.valueOf(2L));

        assertEquals("2", testLobby.getGameId());
        verify(lobbyRepository, times(1)).flush();
    }

    @Test
    void testLobbyRemoveHost(){
        Lobby testLobby = new Lobby();
        testLobby.setLobbyId(1L);

        given(lobbyRepository.findByLobbyId(testLobby.getLobbyId())).willReturn(testLobby);
        lobbyService.removeHost(testLobby.getLobbyId());

        assertEquals("", testLobby.getHost());
        verify(lobbyRepository, times(1)).flush();
    }
}
