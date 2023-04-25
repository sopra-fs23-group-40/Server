package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class PlayerGetDTO {
    private String playerName;
    private String playerId;
    public PlayerGetDTO(String playerName, String playerId) {
        this.playerName = playerName;
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
