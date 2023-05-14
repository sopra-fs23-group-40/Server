package ch.uzh.ifi.hase.soprafs23.rest.dto;

import java.util.HashMap;

public class GameOverDTO {

    private final boolean gameOver;
    private final String winnerName;
    private final int gameDuration;
    private final HashMap<String, Integer> placedBlocks;

    public GameOverDTO(boolean gameOver, String winnerName, int gameDuration, HashMap<String, Integer> placedBlocks) {
        this.gameOver = gameOver;
        this.winnerName = winnerName;
        this.gameDuration = gameDuration;
        this.placedBlocks = placedBlocks;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public String getWinnerName() {
        return winnerName;
    }
    public int getGameDuration() {
        return gameDuration;
    }
    public HashMap<String, Integer> getPlacedBlocks() {
        return placedBlocks;
    }
}