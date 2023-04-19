package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserAuthDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.hibernate.cfg.NotYetImplementedException;
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
    @PostMapping("/lobby")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String createLobby(@RequestBody LobbyPostDTO lobbyPostDTO) {
        throw new NotYetImplementedException("Not yet implemented");
    }

    @GetMapping("/lobbies")
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

    @PostMapping("/joinLobby/{lobbyName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void joinLobby(@PathVariable String lobbyName) {
        throw new NotYetImplementedException("Not yet implemented " + lobbyName);
    }

    @GetMapping("/createLobby")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createLobby(@RequestBody UserAuthDTO userAuthDTO) {
        lobbyService.createLobby(userAuthDTO.getUsername(), userAuthDTO.getToken());
    }

}
