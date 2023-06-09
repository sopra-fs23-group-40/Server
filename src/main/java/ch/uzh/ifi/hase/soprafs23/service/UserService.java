package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.GameStats;
import ch.uzh.ifi.hase.soprafs23.entity.Statistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.StatisticsRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final StatisticsRepository statisticsRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository, @Qualifier("statisticsRepository") StatisticsRepository statisticsRepository) {
    this.userRepository = userRepository;
    this.statisticsRepository = statisticsRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

    public User createUser(User newUser) {
        //so that it won't be possible to register with empty fields through Postman
        //the Frontend checks already if fields are empty or not
        if (newUser.getUsername() == null || newUser.getUsername().length() == 0 ||
                newUser.getPassword() == null || newUser.getPassword().length() == 0) {
            String baseErrorMessage = "Given Username or Password is empty.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage));
        }
        if (newUser.getUsername().length() > 20) {
            String baseErrorMessage = "Username is too long. (max. 20 characters)";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage));
        }
        if(newUser.getUsername().contains(",")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The Username cannot contain the character ,"));
        }
        checkIfUserExists(newUser);
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();
        Statistics statistics = createStatisticsNewUser();
        statistics.setUserId(newUser.getId());
        statisticsRepository.save(statistics);
        statisticsRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private Statistics createStatisticsNewUser() {
        Statistics stat = new Statistics();
        stat.setGamesPlayed(0);
        stat.setGamesWon(0);
        stat.setMinutesPlayed(0);
        stat.setWinPercentage(0);
        stat.setBlocksPlaced(0);
        return stat;
    }

    public User loginUser(User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername != null && Objects.equals(userByUsername.getPassword(), user.getPassword())) {
            if(userByUsername.getStatus() == UserStatus.ONLINE) {
                String baseErrorMessage = "User is already logged in.";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format(baseErrorMessage));
            }
            userByUsername.setStatus(UserStatus.ONLINE);
            userRepository.save(userByUsername);
            userRepository.flush();
            user.setStatus(UserStatus.ONLINE);
            user.setToken(userByUsername.getToken());
            user.setPassword(""); //for security reasons so that the returned user doesn't store the password
            user.setId(userByUsername.getId());
            return user;
        }
        String baseErrorMessage = "User not known or wrong password.";
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format(baseErrorMessage));
    }

    public void logoutUser(String token) {
        User userByToken = userRepository.findByToken(token);
        if (userByToken != null) {
            userByToken.setStatus(UserStatus.OFFLINE);
            userRepository.save(userByToken);
            userRepository.flush();
        }
    }

    public String getLoggedInUsername(String token) {
        User userByToken = userRepository.findByToken(token);
        if (userByToken != null) {
            return userByToken.getUsername();
        }
        //should never return null because the token was just checked prior
        return null;
    }

    // merge with method above?
    public void checkAuthentication(String username, String token) {
        User userByUsername = userRepository.findByUsername(username);
        if (!(userByUsername != null && userByUsername.getToken().equals(token))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "User authentication failed.");
        }
    }

    public Statistics getStatistics(String username) {
      User userByUsername = userRepository.findByUsername(username);
      if (userByUsername == null){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                  "These is no user with this username.");
      }
      return statisticsRepository.findByUserId(userByUsername.getId());
    }

    /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated User which should be created.
   * @throws org.springframework.web.server.ResponseStatusException if username is not unique/already taken
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

    String baseErrorMessage = "User could not be created - provided username is not unique and already taken.";
    if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format(baseErrorMessage));
    }
  }

  public void updateStatistics(Map<String, GameStats> gameStatsMap){
      DecimalFormat df = new DecimalFormat("#.###");
      for(String name: gameStatsMap.keySet()){
          Statistics userStatistics = getStatistics(name);
          GameStats gameStats = gameStatsMap.get(name);
          userStatistics.setGamesWon(userStatistics.getGamesWon() + gameStats.getGamesWon());
          userStatistics.setBlocksPlaced(userStatistics.getBlocksPlaced() + gameStats.getBlocksPlaced());
          userStatistics.setGamesPlayed(userStatistics.getGamesPlayed() + 1);
          userStatistics.setMinutesPlayed(userStatistics.getMinutesPlayed() + gameStats.getMinutesPlayed());
          userStatistics.setWinPercentage(Float.parseFloat(df.format((float)userStatistics.getGamesWon() / (float) userStatistics.getGamesPlayed() * 100)));
      }
      statisticsRepository.flush();
  }

}
