package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyEvent;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserAuthDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.LobbySSE;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LobbyController {

    private final LobbyService lobbyService;
    private final UserService userService;
    private final LobbySSE lobbySse;

    LobbyController(LobbyService lobbyService, UserService userService, LobbySSE lobbySse) {
        this.lobbyService = lobbyService;
        this.userService = userService;
        this.lobbySse = lobbySse;
    }


    @GetMapping("/lobbies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LobbyGetDTO> getLobbies(@RequestHeader(value = "token") String token) {
        List<Lobby> lobbies = lobbyService.getLobbies();
        List<LobbyGetDTO> lobbyGetDTOs = new ArrayList<>();

        // TODO: Authentication with token

        // convert each user to the API representation
        for (Lobby lobby : lobbies) {
            if (lobby.getStatus() == LobbyStatus.WAITING) {
                lobbyGetDTOs.add(DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby));
            }
        }
        return lobbyGetDTOs;
    }

    @PostMapping("/joinLobby")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void joinLobby(@RequestBody LobbyPutDTO lobbyPutDTO, @RequestHeader(value = "token") String token, @RequestHeader(value = "username") String username) {
        UserAuthDTO userAuthDTO = DTOMapper.INSTANCE.convertVariablesToUserAuthDTO(username, token);
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        lobbyService.joinLobby(lobbyPutDTO.getId(), lobbyPutDTO.getPasscode(), userAuthDTO.getUsername());
        lobbySse.send(new LobbyEvent("JOINED", lobbyPutDTO.getId()));
    }

    /***
     *
     * @return Password for the lobby
     */
    @PostMapping("/createLobby")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public long createLobby(@RequestBody UserAuthDTO userAuthDTO) {
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        Lobby created_lobby = lobbyService.createLobby(userAuthDTO.getUsername());
        return created_lobby.getLobbyId();
    }

    @PutMapping("/lobbytype/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyType changeLobbyType(@PathVariable(value = "id") Long id, @RequestBody UserAuthDTO userAuthDTO) {
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        return lobbyService.changeLobbytype(userAuthDTO.getUsername(), id);
    }

    @DeleteMapping("/deletelobby/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteLobby(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token, @RequestHeader(value = "username") String username) {
        UserAuthDTO userAuthDTO = DTOMapper.INSTANCE.convertVariablesToUserAuthDTO(username, token);
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        lobbyService.deleteLobby(userAuthDTO.getUsername(), id);
        lobbySse.send(new LobbyEvent("DELETED", id));
    }

    @GetMapping("/lobby/{id}/checkhost")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean isHost(@PathVariable(value = "id") Long id, @RequestHeader(value = "token") String token, @RequestHeader(value = "username") String username) {
        UserAuthDTO userAuthDTO = DTOMapper.INSTANCE.convertVariablesToUserAuthDTO(username, token);
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        return lobbyService.checkIfHost(userAuthDTO.getUsername(), id);
    }

    @GetMapping("/lobby/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<LobbyGetDTO> getLobby(@PathVariable(value = "id") long id, @RequestHeader(value = "token") String token, @RequestHeader(value = "username") String username) {
        UserAuthDTO userAuthDTO = DTOMapper.INSTANCE.convertVariablesToUserAuthDTO(username, token);
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        Lobby lobby = lobbyService.getLobby(id);
        Lobby ret_lobby = new Lobby();
        ret_lobby.setLobbyId(lobby.getLobbyId());
        ret_lobby.setPlayerList(lobby.getPlayerList());
        ret_lobby.setName(lobby.getName());
        ret_lobby.setLobbyType(lobby.getLobbyType());
        if (lobbyService.checkIfHost(userAuthDTO.getUsername(), id)) {
            ret_lobby.setLobbyToken(lobby.getLobbyToken());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(ret_lobby));
    }

    @PutMapping("/leavelobby/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void leaveLobby(@PathVariable(value = "id") long id, @RequestBody UserAuthDTO userAuthDTO) {
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        lobbyService.leaveLobby(userAuthDTO.getUsername(), id);
        lobbySse.send(new LobbyEvent("LEFT", id));
    }
}
