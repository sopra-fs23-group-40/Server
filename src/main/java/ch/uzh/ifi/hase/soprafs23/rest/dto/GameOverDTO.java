package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.game.Player;

import java.util.HashMap;
import java.util.Optional;

public class GameOverDTO {

    private final boolean gameOver;
    private final String winnerName;
    private final int gameDuration;

    public GameOverDTO(boolean gameOver, String winnerName, int gameDuration, HashMap<String, Integer> placedBlocks) {
        this.gameOver = gameOver;
        this.winnerName = winnerName;
        this.gameDuration = gameDuration;
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
}