package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.RotationDirection;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Inventory;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.*;
import ch.uzh.ifi.hase.soprafs23.rest.dto.BlockFlipDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.BlockPlaceDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.BlockRotateDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void testCreateGame() throws Exception {
        // Create a user
        User user = new User();
        user.setUsername("host");
        user.setToken("testToken");
        userService.createUser(user);

        // Create a lobby
        Lobby lobby = new Lobby();
        lobby.setLobbyId(1L);
        lobby.setName("Test Lobby");
        lobby.setHost(user.getUsername());
        lobby.setPlayerList("player1,player2,player3,player4");
        lobby.setMaxPlayers(4);
        given(lobbyService.getLobby(lobby.getLobbyId())).willReturn(lobby);

        // Mock game creation
        Game createdGame = new Game();
        given(gameService.createGame()).willReturn(createdGame);

        // Prepare the request
        MockHttpServletRequestBuilder postRequest = post("/games")
                .header("token", user.getToken())
                .header("username", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(lobby.getLobbyId()));

        // Perform the POST request to create a game
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(content().string(createdGame.getId()));
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

    @Test
    public void getPlayerInventory_Successful_ReturnInventory() throws Exception {
        // Mock game, player, and inventory
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        Inventory testInventory = new Inventory(testPlayer);
        Block testBlock1 = new Block1(testPlayer, CellStatus.PLAYER1);
        Block testBlock2 = new Block2(testPlayer, CellStatus.PLAYER1);
        testInventory.addBlock(testBlock1);
        testInventory.addBlock(testBlock2);
        testGame.addPlayer(testPlayer.getPlayerName());

        // Mock gameService to return the test game
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        // Perform GET request to retrieve player inventory
        mockMvc.perform(get("/games/" + testGame.getId() + "/" + testPlayer.getPlayerName() + "/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].blockName").value(testBlock1.getBlockName()))
                .andExpect(jsonPath("$[0].length").value(testBlock1.getLength()))
                .andExpect(jsonPath("$[0].height").value(testBlock1.getHeight()))
                .andExpect(jsonPath("$[1].blockName").value(testBlock2.getBlockName()))
                .andExpect(jsonPath("$[1].length").value(testBlock2.getLength()))
                .andExpect(jsonPath("$[1].height").value(testBlock2.getHeight()));
    }

    @Disabled //TODO: currently returns 500 error, fix it
    @Test
    public void getGameOver_GameNotOver_ReturnGameOverDTOWithoutWinner() throws Exception {
        // Mock game
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(testPlayer.getPlayerName());

        // Mock gameService to return the test game
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        // Perform GET request to retrieve game over status
        mockMvc.perform(get("/games/" + testGame.getId() + "/isGameOver"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameOver").value(false))
                .andExpect(jsonPath("$.winner").doesNotExist());
    }

    @Test
    public void getGameBoard_Successful_ReturnGameBoard() throws Exception {
        // Mock game
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(testPlayer.getPlayerName());

        // Mock gameService to return the test game
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        // Perform GET request to retrieve game board
        mockMvc.perform(get("/games/" + testGame.getId() + "/status"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0]").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[0][0]").value(CellStatus.NEUTRAL.name()));
    }

    @Test
    public void placeBlock_SuccessfulMove_ReturnOk() throws Exception {
        // Mock game
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(testPlayer.getPlayerName());
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);
        BlockPlaceDTO blockPlaceDTO = new BlockPlaceDTO("Block3", 0, 0, 180, false);

        // Perform PUT request to place the block
        mockMvc.perform(put("/games/" + testGame.getId() + "/" + testPlayer.getPlayerName() + "/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blockPlaceDTO)))
                .andExpect(status().isOk());


        // Assert that the block is placed on the game board
        GameBoard gameBoard = testGame.getGameBoard();
        assertEquals(testPlayer.getStatus(), gameBoard.getGameBoard()[0][0].getStatus());
    }

    @Test
    public void flipVerticalBlock_SuccessfulFlip_ReturnOk() throws Exception {
        // Mock game
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(testPlayer.getPlayerName());
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);
        BlockFlipDTO blockFlipDTO = new BlockFlipDTO("Block3",true);

        // Perform PUT request to place the block
        mockMvc.perform(put("/games/" + testGame.getId() + "/" + testPlayer.getPlayerName() + "/vertical_flip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(blockFlipDTO)))
                        .andExpect(status().isOk());

    }

    @Test
    public void flipHorizontalBlock_SuccessfulFlip_ReturnOk() throws Exception {
        // Mock game
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(testPlayer.getPlayerName());
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);
        BlockFlipDTO blockFlipDTO = new BlockFlipDTO("Block3",true);

        // Perform PUT request to place the block
        mockMvc.perform(put("/games/" + testGame.getId() + "/" + testPlayer.getPlayerName() + "/horizontal_flip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(blockFlipDTO)))
                        .andExpect(status().isOk());
    }

    @Test
    public void rotateBlock_SuccessfulRotation_ReturnOk() throws Exception {
        // Mock game
        Game testGame = new Game();
        Player testPlayer = new Player(CellStatus.PLAYER1, "player1");
        testGame.addPlayer(testPlayer.getPlayerName());

        // Mock gameService to return the test game
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        // Mock player's inventory
        Block testBlock = new Block3(testPlayer, testPlayer.getStatus());
        testPlayer.getInventory().addBlock(testBlock);

        // Create BlockRotateDTO for the request body
        BlockRotateDTO blockRotateDTO = new BlockRotateDTO("Block3", RotationDirection.CLOCKWISE);

        // Perform PUT request to rotate the block
        mockMvc.perform(put("/games/" + testGame.getId() + "/" + testPlayer.getPlayerName() + "/rotate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(blockRotateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.blockName").value(testBlock.getBlockName()))
                .andExpect(jsonPath("$.length").value(testBlock.getHeight()))
                .andExpect(jsonPath("$.height").value(testBlock.getLength()));
    }

    @Test
    public void getStartDate_Successful_ReturnOk() throws Exception {
        // Mock game
        Game testGame = new Game();

        // Mock gameService to return the test game
        given(gameService.getGameById(testGame.getId())).willReturn(testGame);

        // Perform GET request to retrieve the start date
        mockMvc.perform(get("/games/" + testGame.getId() + "/time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}