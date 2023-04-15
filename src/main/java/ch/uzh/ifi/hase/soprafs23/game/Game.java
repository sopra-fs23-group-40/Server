package ch.uzh.ifi.hase.soprafs23.game;
import java.time.LocalDateTime;
import java.util.UUID;

import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;


public class Game {
    private String gameId;
    private GameBoard gameboard;
    private Player[] players;
    private Player currentPlayer;
    private Object creationDate;

    public Game() {
        this.gameId = UUID.randomUUID().toString();
        this.gameboard = new GameBoard();
        this.players = new Player[2];
        this.players[0] = new Player(CellStatus.PLAYER1);
        this.players[1] = new Player(CellStatus.PLAYER2);
        this.currentPlayer = this.players[0];
        //this.gameStatus = GameStatus.WAITING_FOR_PLAYER;
        this.creationDate = LocalDateTime.now();
    }
}

