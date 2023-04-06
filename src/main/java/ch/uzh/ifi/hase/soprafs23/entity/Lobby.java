package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyStatus;
import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "LOBBY")
public class Lobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long lobby_id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String lobby_token;

    @Column(nullable = false)
    private LobbyStatus status;

    @Column(nullable = false)
    private LobbyType is_private;

    public Long getLobby_id() {
        return lobby_id;
    }

    public void setLobby_id(Long lobby_id) {
        this.lobby_id = lobby_id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLobby_token() {
        return lobby_token;
    }

    public void setLobby_token(String lobby_token) {
        this.lobby_token = lobby_token;
    }

    public LobbyStatus getStatus() {
        return status;
    }

    public void setStatus(LobbyStatus status) {
        this.status = status;
    }

    public LobbyType getIs_private() {
        return is_private;
    }

    public void setIs_private(LobbyType is_private) {
        this.is_private = is_private;
    }
}
