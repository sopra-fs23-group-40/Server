package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Statistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.StatsGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

    @MockBean
    private TokenAuthenticationService tokenAuthenticationService;

    @MockBean
  private UserService userService;


  @Test
   void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
    // given
    User user = new User();
    user.setPassword("Password");
    user.setUsername("firstname@lastname");
    user.setStatus(UserStatus.OFFLINE);
    String token = "12345";


      List<User> allUsers = Collections.singletonList(user);

    // this mocks the UserService -> we define above what the userService should
    // return when getUsers() is called
    given(userService.getUsers()).willReturn(allUsers);
    given(tokenAuthenticationService.checkToken(token)).willReturn(true);

    // when
    MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON).header("token", token);

    // then
    mockMvc.perform(getRequest).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].username", is(user.getUsername())))
        .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())));
  }

  @Test
   void createUser_validInput_userCreated() throws Exception {
    // given
    User user = new User();
    user.setId(1L);
    user.setPassword("Test User");
    user.setUsername("testUsername");
    user.setToken("1");
    user.setStatus(UserStatus.ONLINE);

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setPassword("Test User");
    userPostDTO.setUsername("testUsername");

    given(userService.createUser(Mockito.any())).willReturn(user);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

    // then
    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
  }

    @Test
     void loginUser_validInput_userLoggedIn() throws Exception {
        // given
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");

        User loggedInUser = new User();
        loggedInUser.setId(1L);
        loggedInUser.setPassword("Test User");
        loggedInUser.setUsername("testUsername");
        loggedInUser.setToken("12345");
        loggedInUser.setStatus(UserStatus.ONLINE);
        given(userService.loginUser(Mockito.any())).willReturn(loggedInUser);

        // when
        MockHttpServletRequestBuilder postRequest = post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(loggedInUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(loggedInUser.getUsername())))
                .andExpect(jsonPath("$.status", is(loggedInUser.getStatus().toString())))
                .andExpect(header().exists("token"));
    }

    @Test
     void logoutUser_validToken_userLoggedOut() throws Exception {
        // given
        User user = new User();
        user.setUsername("testUsername");
        user.setToken("testToken");
        userService.createUser(user);
        given(tokenAuthenticationService.checkToken(user.getToken())).willReturn(true);

        // when
        MockHttpServletRequestBuilder postRequest = post("/logout")
                .header("token", user.getToken());

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk());
        verify(userService).logoutUser(user.getToken());
    }

    @Test
     void getLoggedInUsername_validToken_returnUsername() throws Exception {
        // given
        User user = new User();
        user.setUsername("testUsername");
        user.setToken("testToken");
        userService.createUser(user);
        given(tokenAuthenticationService.checkToken(user.getToken())).willReturn(true);
        given(userService.getLoggedInUsername(user.getToken())).willReturn(user.getUsername());

        // when
        MockHttpServletRequestBuilder getRequest = get("/loggedInName")
                .header("token", user.getToken());

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(user.getUsername()));
    }

    @Test
     void getUserStatistics_validUsername_returnStatistics() throws Exception {
        // given
        String username = "testUsername";
        StatsGetDTO expectedStatsGetDTO = new StatsGetDTO();
        expectedStatsGetDTO.setBlocksPlaced(10);
        expectedStatsGetDTO.setGamesPlayed(1);
        expectedStatsGetDTO.setGamesWon(0);

        Statistics userStatistics = new Statistics();
        userStatistics.setBlocksPlaced(10);
        userStatistics.setGamesPlayed(1);
        userStatistics.setGamesWon(0);
        given(userService.getStatistics(username)).willReturn(userStatistics);

        // when
        MockHttpServletRequestBuilder getRequest = get("/statistics")
                .header("username", username);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(expectedStatsGetDTO)));
    }




    /**
   * Helper Method to convert userPostDTO into a JSON string such that the input
   * can be processed
   * Input will look like this: {"name": "Test User", "username": "testUsername"}
   * 
   * @param object
   * @return string
   */
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format("The request body could not be created.%s", e.toString()));
    }
  }
}