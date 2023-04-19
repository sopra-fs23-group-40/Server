package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Lobby> getLobbies(){return this.lobbyRepository.findAll();}

    public void createLobby(String username) {

        Lobby newLobby = new Lobby();

        String lobbyToken = UUID.randomUUID().toString().substring(0, 7);

        newLobby.setHost(username);
        newLobby.setLobbyToken(lobbyToken);
        newLobby.setStatus(LobbyStatus.WAITING);
        newLobby.setLobbyType(LobbyType.PRIVATE);
        newLobby.setName(username + "'s Lobby");

        lobbyRepository.save(newLobby);

    }
}
