package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(LobbyService.class);

    private final LobbyRepository lobbyRepository;

    @Autowired
    public LobbyService(@Qualifier("lobbyRepository") LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public List<Lobby> getLobbies() {
        return this.lobbyRepository.findAll();
    }

    public Lobby createLobby(String username) {
        if (lobbyRepository.findByHost(username) != null) {
            String baseErrorMessage = "This user has already created a lobby";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }

        Lobby newLobby = new Lobby();
        String lobbyToken = UUID.randomUUID().toString().substring(0, 7);

        newLobby.setHost(username);
        newLobby.setLobbyToken(lobbyToken);
        newLobby.setStatus(LobbyStatus.WAITING);
        newLobby.setLobbyType(LobbyType.PRIVATE);
        newLobby.setName(username + "'s Lobby");

        lobbyRepository.save(newLobby);
        lobbyRepository.flush();
        return newLobby;
    }

    public LobbyType change_lobbytype(String username) {
        Lobby this_lobby = lobbyRepository.findByHost(username);
        if (this_lobby == null) {
            String baseErrorMessage = "The user isn't the host of this Lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        if (this_lobby.getLobbyType() == LobbyType.PRIVATE) {
            this_lobby.setLobbyType(LobbyType.PUBLIC);
        }
        else {
            this_lobby.setLobbyType(LobbyType.PRIVATE);
        }
        return this_lobby.getLobbyType();
    }
}
