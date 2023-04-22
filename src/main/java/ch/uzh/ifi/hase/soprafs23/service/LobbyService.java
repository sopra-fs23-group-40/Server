package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
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

    //private final Logger log = LoggerFactory.getLogger(LobbyService.class);

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
        newLobby.setPlayerList(username);
        newLobby.setHost(username);
        newLobby.setLobbyToken(lobbyToken);
        newLobby.setStatus(LobbyStatus.WAITING);
        newLobby.setLobbyType(LobbyType.PRIVATE);
        newLobby.setName(username + "'s Lobby");

        lobbyRepository.save(newLobby);
        lobbyRepository.flush();
        return newLobby;
    }

    public LobbyType changeLobbytype(String username, long id) {
        Lobby this_lobby = lobbyRepository.findByHost(username);
        Lobby id_lobby = lobbyRepository.findByLobbyId(id);
        if (this_lobby == null) {
            String baseErrorMessage = "The user isn't the host of any lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        if (id_lobby == null){
            String baseErrorMessage = "There is no lobby with this id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }

        if (this_lobby != id_lobby){
            String baseErrorMessage = "This user isn't the host of the chosen lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }

        if (id_lobby.getLobbyType() == LobbyType.PRIVATE) {
            id_lobby.setLobbyType(LobbyType.PUBLIC);
        }
        else {
            id_lobby.setLobbyType(LobbyType.PRIVATE);
        }
        return id_lobby.getLobbyType();
    }

    public void deleteLobby(String username){
        Lobby this_lobby = lobbyRepository.findByHost(username);
        if (this_lobby == null){
            String baseErrorMessage = "The user doesn't have a lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        lobbyRepository.delete(this_lobby);
        lobbyRepository.flush();
    }

    public boolean checkIfHost(String username, long id){
        Lobby this_lobby = lobbyRepository.findByLobbyId(id);
        if (this_lobby == null){
            String baseErrorMessage = "There is no lobby with this Id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        return this_lobby.getHost().equals(username);
    }

    public Lobby getLobby(long id) {
        Lobby this_lobby = lobbyRepository.findByLobbyId(id);
        if (this_lobby == null){
            String baseErrorMessage = "There is no lobby with this Id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        return this_lobby;
    }

}
