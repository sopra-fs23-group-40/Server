package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.service.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenAuthenticationService tokenAuthenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private LobbyService lobbyService;

    @MockBean
    private GameService gameService;

    @Disabled
    @Test
    public void testCreateGame() throws Exception {
        User user = new User();
        user.setUsername("player1");
        user.setToken("testToken");
        userService.createUser(user);

        Lobby lobby = new Lobby();
        lobby.setLobbyId(1L);
        lobby.setName("Test Lobby");
        lobby.setHost(user.getUsername());
        lobby.setPlayerList("player1,player2,player3,player4");
        lobby.setMaxPlayers(4);
        given(lobbyService.getLobby(lobby.getLobbyId())).willReturn(lobby);

        // when
        MockHttpServletRequestBuilder postRequest = post("/games")
                .header("token", user.getToken())
                .header("username", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(lobby.getLobbyId()));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void getCurrentPlayer_Successful_ReturnOk() throws Exception {
        // given
        Game testGame = new Game();
        Player currentPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(currentPlayer.getPlayerName());
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        PlayerGetDTO expectedResponse = new PlayerGetDTO(currentPlayer.getPlayerName(), currentPlayer.getPlayerId());

        // when
        MockHttpServletRequestBuilder getRequest = get("/games/" + testGame.getId() + "/currentPlayer")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.playerName").value(expectedResponse.getPlayerName()));
    }

    @Test
    public void getPlayers_Successful_ReturnOk() throws Exception {
        // given
        Game testGame = new Game();
        Player player1 = new Player(CellStatus.PLAYER1, "player1");
        Player player2 = new Player(CellStatus.PLAYER2, "player2");
        testGame.addPlayer(player1.getPlayerName());
        testGame.addPlayer(player2.getPlayerName());
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        List<PlayerGetDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(new PlayerGetDTO(player1.getPlayerName(), player1.getPlayerId()));
        expectedResponse.add(new PlayerGetDTO(player2.getPlayerName(), player2.getPlayerId()));

        // when
        MockHttpServletRequestBuilder getRequest = get("/games/" + testGame.getId() + "/players")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].playerName").value(expectedResponse.get(0).getPlayerName()))
                .andExpect(jsonPath("$[1].playerName").value(expectedResponse.get(1).getPlayerName()));
    }

    @Test
    public void addPlayerToGame_Successful_ReturnCreated() throws Exception {
        // given
        Game testGame = new Game();
        Player player1 = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(player1.getPlayerName());
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);


        // when
        MockHttpServletRequestBuilder postRequest = post("/games/" + testGame.getId() + "/players")
                .content(player1.getPlayerName())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }
}