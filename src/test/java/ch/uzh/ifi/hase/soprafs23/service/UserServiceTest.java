package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Statistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.StatisticsRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private StatisticsRepository statisticsRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setId(1L);
    testUser.setPassword("testPass");
    testUser.setUsername("testUsername");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
   void createUser_validInputs_success() {
    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    User createdUser = userService.createUser(testUser);

    // then
    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getPassword(), createdUser.getPassword());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.ONLINE, createdUser.getStatus());
  }

  @Test
   void createUser_duplicateName_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
   void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

    @Test
    void loginUser_validCredentials_success() {
        // given
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");

        User userByUsername = new User();
        userByUsername.setId(1L);
        userByUsername.setUsername("testUsername");
        userByUsername.setPassword("testPassword");
        userByUsername.setStatus(UserStatus.OFFLINE);
        userByUsername.setToken("token123");

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(userByUsername);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userByUsername);

        // when
        User loggedInUser = userService.loginUser(user);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals(userByUsername.getId(), loggedInUser.getId());
        assertEquals(userByUsername.getUsername(), loggedInUser.getUsername());
        assertEquals(UserStatus.ONLINE, loggedInUser.getStatus());
        assertEquals(userByUsername.getToken(), loggedInUser.getToken());
    }

    @Test
    void logoutUser_validToken_success() {
        // given
        String token = "token123";

        User userByToken = new User();
        userByToken.setId(1L);
        userByToken.setUsername("testUsername");
        userByToken.setStatus(UserStatus.ONLINE);
        userByToken.setToken(token);

        Mockito.when(userRepository.findByToken(Mockito.any())).thenReturn(userByToken);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(userByToken);

        // when
        userService.logoutUser(token);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals(UserStatus.OFFLINE, userByToken.getStatus());
    }

    @Test
    void getLoggedInUsername_validToken_success() {
        // given
        String token = "token123";

        User userByToken = new User();
        userByToken.setId(1L);
        userByToken.setUsername("testUsername");

        Mockito.when(userRepository.findByToken(Mockito.any())).thenReturn(userByToken);

        // when
        String loggedInUsername = userService.getLoggedInUsername(token);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findByToken(Mockito.any());
        assertEquals(userByToken.getUsername(), loggedInUsername);
    }

    @Test
    void checkAuthentication_validCredentials_success() {
        // given
        String username = "testUsername";
        String token = "token123";

        User userByUsername = new User();
        userByUsername.setId(1L);
        userByUsername.setUsername("testUsername");
        userByUsername.setToken("token123");

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(userByUsername);

        // when/then
        assertDoesNotThrow(() -> userService.checkAuthentication(username, token));
    }

    @Test
    void checkAuthentication_invalidCredentials_throwsException() {
        // given
        String username = "testUsername";
        String token = "invalidToken";

        User userByUsername = new User();
        userByUsername.setId(1L);
        userByUsername.setUsername("testUsername");
        userByUsername.setToken("token123");

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(userByUsername);

        // when/then
        assertThrows(ResponseStatusException.class, () -> userService.checkAuthentication(username, token));
    }

    @Test
    void getStatistics_existingUsername_success() {
        // given
        String username = "testUsername";
        Long userId = 1L;

        User userByUsername = new User();
        userByUsername.setId(userId);
        userByUsername.setUsername(username);

        Statistics userStatistics = new Statistics();
        userStatistics.setId(1L);
        userStatistics.setUserId(userId);

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(userByUsername);
        Mockito.when(statisticsRepository.findByUserId(Mockito.anyLong())).thenReturn(userStatistics);

        // when
        Statistics result = userService.getStatistics(username);

        // then
        assertNotNull(result);
        assertEquals(userStatistics.getId(), result.getId());
        assertEquals(userStatistics.getUserId(), result.getUserId());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.any());
        Mockito.verify(statisticsRepository, Mockito.times(1)).findByUserId(Mockito.anyLong());
    }

    @Test
    void getStatistics_nonExistingUsername_throwsException() {
        // given
        String username = "nonExistingUsername";

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);

        // when/then
        assertThrows(ResponseStatusException.class, () -> userService.getStatistics(username));
    }
}
