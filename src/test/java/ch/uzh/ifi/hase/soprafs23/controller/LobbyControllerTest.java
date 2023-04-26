package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserAuthDTO;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.SSE;
import ch.uzh.ifi.hase.soprafs23.service.TokenAuthenticationService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

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

    @MockBean
    private SSE sse;

    @Test
    public void createLobby_Successful_ReturnStatusOK() throws Exception {
        // given
        User user = new User();
        user.setUsername("testUsername");
        user.setToken("validToken");
        userService.createUser(user);

        Lobby lobby = new Lobby();
        lobby.setLobbyId(1L);
        lobby.setName("Test Lobby");
        lobby.setHost(user.getUsername());
        given(lobbyService.createLobby(user.getUsername())).willReturn(lobby);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(user.getUsername());
        userAuthDTO.setToken(user.getToken());

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
        User user = new User();
        user.setUsername("testUsername");
        user.setToken("validToken");
        userService.createUser(user);

        Lobby lobby = new Lobby();
        lobby.setLobbyId(1L);
        lobby.setName("Test Lobby");
        lobby.setHost(user.getUsername());
        lobby.setLobbyType(LobbyType.PUBLIC);
        given(lobbyService.changeLobbytype(user.getUsername(), lobby.getLobbyId())).willReturn(LobbyType.PRIVATE);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(user.getUsername());
        userAuthDTO.setToken(user.getToken());

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
        String token = "validToken";
        String username = "testUsername";
        User user = new User();
        user.setUsername(username);
        user.setToken(token);
        userService.createUser(user);
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
        User user = new User();
        user.setUsername("testUsername");
        user.setToken("validToken");
        userService.createUser(user);

        Lobby lobby = new Lobby();
        lobby.setLobbyId(1L);
        lobby.setName("Test Lobby");
        lobby.setHost(user.getUsername());
        lobby.setLobbyType(LobbyType.PUBLIC);
        lobbyService.createLobby(user.getUsername());
        given(lobbyService.getLobby(1L)).willReturn(lobby);

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUsername(user.getUsername());
        userAuthDTO.setToken(user.getToken());

        //given(lobbyService.checkIfHost(userAuthDTO.getUsername(), 1L)).willReturn(true);

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobby/1")
                .header("token", user.getToken())
                .header("username", user.getUsername())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lobbyId").value(1))
                .andExpect(jsonPath("$.name").value(lobby.getName()))
                .andExpect(jsonPath("$.lobbyType").value("PUBLIC"));
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
