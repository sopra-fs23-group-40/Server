package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LobbyController {


    /***
     *
     * @param lobbyPostDTO: includes the token and a username of the host
     * @return Password for the lobby
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String createLobby(@RequestBody LobbyPostDTO lobbyPostDTO) {
        return "";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LobbyGetDTO> getLobbies(@RequestHeader(value = "token") String token) {
        return null;
    }

    @PutMapping
    @ResponseStatus
    @ResponseBody
    public void joinLobby() {

    }

}
