package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.service.TokenAuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;
    private final TokenAuthenticationService tokenAuthenticationService;

    UserController(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    /***
     * returns a Header with the user token in it
     */
    private HttpHeaders getHeader(User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("token",
                user.getToken());
        responseHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "token");
        return responseHeaders;
    }

    /***
     * checks if a user with such a token is found in the userRepository
     */
    private void checkToken(String token) {
        if (!tokenAuthenticationService.checkToken(token)) {
            String baseErrorMessage = "User not authenticated.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(baseErrorMessage));
        }
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers(@RequestHeader(value = "token") String token) {
        checkToken(token);
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    /***
     * registers a new user based on the username and password given
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<UserGetDTO> createUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);
        // convert internal representation of user back to API and include header with token
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(getHeader(createdUser)) //user token
                .body(DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser));
    }

    /***
     * logs user in based on the given username and password
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<UserGetDTO> loginUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        User loggedInUser = userService.loginUser(userInput);

        // convert internal representation of user back to API
        return ResponseEntity.status(HttpStatus.OK)
                .headers(getHeader(loggedInUser)) //user token
                .body(DTOMapper.INSTANCE.convertEntityToUserGetDTO(loggedInUser));
    }

    /***
     * logs the user out based on if the user is authenticated/ the token
     */
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void logoutUser(@RequestHeader(value = "token") String token) {
        checkToken(token);
        userService.logoutUser(token);

    }

    /***
     * returns the username of a user based on the token to display it on /game/dashboard
     */
    @GetMapping("/loggedInName")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getLoggedInUsername(@RequestHeader(value = "token") String token) {
        checkToken(token);
        return userService.getLoggedInUsername(token);

    }
}
