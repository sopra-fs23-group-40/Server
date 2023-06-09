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
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class LobbyService {

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
        newLobby.setCurrentPlayers(1);

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
        if (id_lobby == null) {
            String baseErrorMessage = "There is no lobby with this id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }

        if (this_lobby != id_lobby) {
            String baseErrorMessage = "This user isn't the host of the chosen lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }

        if (id_lobby.getLobbyType() == LobbyType.PRIVATE) {
            id_lobby.setLobbyType(LobbyType.PUBLIC);
        }
        else {
            id_lobby.setLobbyType(LobbyType.PRIVATE);
        }
        lobbyRepository.save(id_lobby);
        lobbyRepository.flush();
        return id_lobby.getLobbyType();
    }

    public void deleteLobby(String username, long id) {
        Lobby id_lobby = lobbyRepository.findByLobbyId(id);
        Lobby host_lobby = lobbyRepository.findByHost(username);
        if (host_lobby == null || id_lobby == null) {
            String baseErrorMessage = "The user doesn't have a lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        if (host_lobby != id_lobby){
            String baseErrorMessage = "This user isn't the host of the lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        lobbyRepository.delete(id_lobby);
        lobbyRepository.flush();
    }

    public boolean checkIfHost(String username, long id) {
        Lobby this_lobby = lobbyRepository.findByLobbyId(id);
        if (this_lobby == null) {
            String baseErrorMessage = "There is no lobby with this Id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        return this_lobby.getHost().equals(username);
    }

    public Lobby getLobby(long id) {
        Lobby this_lobby = lobbyRepository.findByLobbyId(id);
        if (this_lobby == null) {
            String baseErrorMessage = "There is no lobby with this Id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        return this_lobby;
    }

    private void join(Lobby lobby, String username) {
        String newPlayerList = lobby.getPlayerList()+","+username;
        lobby.setPlayerList(newPlayerList);
        lobby.setCurrentPlayers(lobby.getCurrentPlayers() + 1);
        lobbyRepository.save(lobby);
        lobbyRepository.flush();
    }

    public void joinLobby(Long id, String passcode, String username) {
        Lobby lobby = getLobby(id);

        if(lobby.getCurrentPlayers() >= lobby.getMaxPlayers()) {
            String baseErrorMessage = "The lobby is already full!";
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage));
        }

        String[] list = lobby.getPlayerList().split(",");
        for (String s : list) {
            if (s.equals(username)) {
                String baseErrorMessage = "The player is already in the lobby!";
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage));
            }
        }

        if(lobby.getLobbyType() == LobbyType.PUBLIC) {
            join(lobby, username);
        }
        else {
            if(!Objects.equals(lobby.getLobbyToken(), passcode)) {
                String baseErrorMessage = "Wrong passcode!";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
            }
            join(lobby, username);
        }
    }
    public void leaveLobby(String username, long id) {
        Lobby this_lobby = lobbyRepository.findByLobbyId(id);
        if (this_lobby == null) {
            String baseErrorMessage = "There is no lobby with this Id!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        String[] list = this_lobby.getPlayerList().split(",");
        boolean inList = false;
        boolean first = true;
        String newlist = "";
        for (String s : list) {
            if (s.equals(username)) {
                inList = true;
            }
            else {
                if (first) {
                    newlist = newlist + s;
                    first = false;
                }
                else {
                    newlist = newlist + "," + s;
                }
            }
        }
        if (!inList) {
            String baseErrorMessage = "There is no player with that username in the lobby!";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage));
        }
        this_lobby.setPlayerList(newlist);
        this_lobby.setCurrentPlayers(this_lobby.getCurrentPlayers()-1);
        lobbyRepository.save(this_lobby);
        lobbyRepository.flush();
    }

    public void setStatus(long lobbyId, LobbyStatus newStatus){
        Lobby lobbyToChange = getLobby(lobbyId);
        lobbyToChange.setStatus(newStatus);
        lobbyRepository.flush();
    }

    public void setGameId(long lobbyId, String gameId){
        Lobby lobbyToChange = getLobby(lobbyId);
        lobbyToChange.setGameId(gameId);
        lobbyRepository.flush();
    }

    public void removeHost(long lobbyId){
        Lobby lobbyToChange = getLobby(lobbyId);
        lobbyToChange.setHost("");
        lobbyRepository.flush();
    }
}
