package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LobbyController {

    private final LobbyService lobbyService;

    LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

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
        List<Lobby> lobbies = lobbyService.getLobbies();
        List<LobbyGetDTO> lobbyGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (Lobby lobby : lobbies) {
            if (lobby.getStatus() == LobbyStatus.WAITING) {
                lobbyGetDTOs.add(DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby));
            }
        }
        return lobbyGetDTOs;
    }

    @PutMapping
    @ResponseStatus
    @ResponseBody
    public void joinLobby() {

    }

}
