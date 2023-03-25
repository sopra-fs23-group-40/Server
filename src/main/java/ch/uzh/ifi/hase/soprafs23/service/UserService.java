package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
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
        checkIfUserExists(newUser);
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        // saves the given entity but data is only persisted in the database once
        // flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User loginUser(User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername != null && Objects.equals(userByUsername.getPassword(), user.getPassword())) {
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

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

    String baseErrorMessage = "The username provided is not unique and already taken. Therefore, the user could not be created!";
    if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format(baseErrorMessage));
    }
  }
}
