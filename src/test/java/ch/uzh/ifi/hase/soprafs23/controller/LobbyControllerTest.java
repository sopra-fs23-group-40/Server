package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserAuthDTO;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.TokenAuthenticationService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(LobbyController.class)
class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenAuthenticationService tokenAuthenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private LobbyService lobbyService;


    private User createTestUser(){
        User createdUser = new User();
        createdUser.setUsername("testUsername");
        createdUser.setToken("testToken");
        return createdUser;
    }

    private Lobby createTestLobby(){
        Lobby createdLobby = new Lobby();
        createdLobby.setLobbyId(1L);
        createdLobby.setName("Test Lobby");
        createdLobby.setHost("testUsername");
        return createdLobby;
    }

    @Test
    public void createLobby_Successful_ReturnStatusOK() throws Exception {
        // given
        User testUser = createTestUser();
        userService.createUser(testUser);

        Lobby testLobby = createTestLobby();
        given(lobbyService.createLobby(testUser.getUsername())).willReturn(testLobby);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(testUser.getUsername());
        userAuthDTO.setToken(testUser.getToken());

        // when
        MockHttpServletRequestBuilder postRequest = post("/createLobby")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userAuthDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void changeLobbyType_Successful_ReturnStatusOK() throws Exception {
        // given
        User testUser = createTestUser();
        userService.createUser(testUser);

        Lobby testLobby = createTestLobby();
        testLobby.setLobbyType(LobbyType.PUBLIC);
        given(lobbyService.changeLobbytype(testUser.getUsername(), testLobby.getLobbyId())).willReturn(LobbyType.PRIVATE);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(testUser.getUsername());
        userAuthDTO.setToken(testUser.getToken());

        // when
        MockHttpServletRequestBuilder putRequest = put("/lobbytype/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userAuthDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(LobbyType.PRIVATE.toString()));
    }

    @Test
    public void isHost_Valid_ReturnStatusOK() throws Exception {
        // given
        Long lobbyId = 1L;
        String token = "testToken";
        String username = "testUsername";
        User testUser = createTestUser();
        userService.createUser(testUser);
        given(lobbyService.checkIfHost(username, lobbyId)).willReturn(true);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(username);
        userAuthDTO.setToken(token);

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobby/" + lobbyId + "/checkhost")
                .header("token", token)
                .header("username", username);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetLobby() throws Exception {
        // given
        User testUser = createTestUser();
        userService.createUser(testUser);

        Lobby testLobby = createTestLobby();
        testLobby.setLobbyType(LobbyType.PUBLIC);
        lobbyService.createLobby(testUser.getUsername());
        given(lobbyService.getLobby(1L)).willReturn(testLobby);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(testUser.getUsername());
        userAuthDTO.setToken(testUser.getToken());

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobby/1")
                .header("token", testUser.getToken())
                .header("username", testUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lobbyId").value(1))
                .andExpect(jsonPath("$.name").value(testLobby.getName()))
                .andExpect(jsonPath("$.lobbyType").value("PUBLIC"));
    }

    @Test
    public void testGetLobbies() throws Exception {
        // given
        User user = createTestUser();
        userService.createUser(user);

        List<Lobby> lobbies = new ArrayList<>();
        Lobby testLobby1 = createTestLobby();
        testLobby1.setName("Test Lobby 1");
        testLobby1.setStatus(LobbyStatus.WAITING);
        lobbies.add(testLobby1);

        Lobby lobby2 = createTestLobby();
        lobby2.setLobbyId(2L);
        lobby2.setName("Test Lobby 2");
        lobby2.setStatus(LobbyStatus.INGAME);
        lobbies.add(lobby2);

        given(lobbyService.getLobbies()).willReturn(lobbies);

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobbies")
                .header("token", user.getToken())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lobbyId").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Lobby 1"));
    }

    @Test
    public void testJoinLobby() throws Exception {
        // given
        User testUser = createTestUser();
        userService.createUser(testUser);

        LobbyPutDTO lobbyPutDTO = new LobbyPutDTO();
        lobbyPutDTO.setId(1L);
        lobbyPutDTO.setPasscode("1234");

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(testUser.getUsername());
        userAuthDTO.setToken(testUser.getToken());

        // when
        MockHttpServletRequestBuilder postRequest = post("/joinLobby")
                .header("token", testUser.getToken())
                .header("username", testUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(lobbyPutDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isAccepted());

        Mockito.verify(lobbyService, Mockito.times(1)).joinLobby(eq(1L), eq("1234"), eq(testUser.getUsername()));
    }

    @Test
    public void testDeleteLobby() throws Exception {
        // given
        User testUser = createTestUser();
        userService.createUser(testUser);

        Lobby testLobby = createTestLobby();
        given(lobbyService.getLobby(1L)).willReturn(testLobby);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(testUser.getUsername());
        userAuthDTO.setToken(testUser.getToken());

        // when
        MockHttpServletRequestBuilder deleteRequest = delete("/deletelobby/1")
                .header("token", testUser.getToken())
                .header("username", testUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(deleteRequest)
                .andExpect(status().isOk());

        Mockito.verify(lobbyService, Mockito.times(1)).deleteLobby(testUser.getUsername(), 1L);
    }

    @Test
    public void testLeaveLobby() throws Exception {
        // given
        String username = "testUsername";
        String token = "validToken";
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(username);
        userAuthDTO.setToken(token);

        // when
        MockHttpServletRequestBuilder putRequest = put("/leavelobby/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userAuthDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isOk());

        Mockito.verify(lobbyService).leaveLobby(userAuthDTO.getUsername(), 1L);
    }


    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
