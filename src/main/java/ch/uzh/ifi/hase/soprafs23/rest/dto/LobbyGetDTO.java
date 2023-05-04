package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;

public class LobbyGetDTO {
    private String name;
    private long lobbyId;
    private LobbyType lobbyType;
    private String lobbyToken;
    private String playerList;
    private int currentPlayers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public LobbyType getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(LobbyType lobbyType) {
        this.lobbyType = lobbyType;
    }

    public String getLobbyToken() {
        return lobbyToken;
    }

    public void setLobbyToken(String lobbyToken) {
        this.lobbyToken = lobbyToken;
    }

    public String getPlayerList() {
        return playerList;
    }

    public void setPlayerList(String playerList) {
        this.playerList = playerList;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
}
