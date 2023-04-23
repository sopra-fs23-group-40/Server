package ch.uzh.ifi.hase.soprafs23.game;
import java.time.LocalDateTime;
import java.util.UUID;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;


public class Game {
    private String gameId;
    private GameBoard gameboard;
    private Player[] players;
    private Player currentPlayer;
    private Object creationDate;

    public Game() {
        this.gameId = UUID.randomUUID().toString();
        this.gameboard = new GameBoard();
        this.players = new Player[4];
        this.players[0] = new Player(CellStatus.PLAYER1);
        this.players[1] = new Player(CellStatus.PLAYER2);
        this.players[2] = new Player(CellStatus.PLAYER3);
        this.players[3] = new Player(CellStatus.PLAYER4);
        this.currentPlayer = this.players[0];
        //this.gameStatus = GameStatus.WAITING_FOR_PLAYER;
        this.creationDate = LocalDateTime.now();
    }

    public boolean isGameOver() {
        for (Player player : players) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    for (Block block : player.getInventory().getUnplayedBlocks()) {
                        for (int flip = 0; flip < 1; flip++) {
                            for (int rot = 0; rot < 4; rot++) {
                                if (gameboard.canPlacePiece(i, j, block)) {
                                    return false;
                                }
                                block.rotateClockwise();
                            }
                            block.rotateClockwise();
                            block.flipVertical();
                        }
                    }
                }
            }
        }
        return true;
    }

    public GameBoard getGameBoard() {
        return gameboard;
    }

    public void addPlayer(Player player) {
        // Check if the game already has four players
        if (players.length >= 4) {
            throw new RuntimeException("Game already has four players");
        }

        // Find the next free slot in the players array
        int nextSlot = -1;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                nextSlot = i;
                break;
            }
        }

        // Add the player to the next free slot
        if (nextSlot != -1) {
            players[nextSlot] = player;
        }
    }
}
