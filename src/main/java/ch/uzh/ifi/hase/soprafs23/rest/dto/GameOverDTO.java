package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.game.Player;

import java.util.Optional;

public class GameOverDTO {

    private boolean gameOver;
    private Optional<Player> winner;
    private int gameDuration;

    public GameOverDTO(boolean gameOver, Optional<Player> winner, int gameDuration) {
        this.gameOver = gameOver;
        this.winner = winner;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public Optional<Player> getWinner() {
        return winner;
    }
    public int getGameDuration() {
        return gameDuration;
    }
}