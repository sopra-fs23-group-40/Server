package ch.uzh.ifi.hase.soprafs23.game;
import java.time.LocalDateTime;
import java.util.List;
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
        this.players[0] = new Player(CellStatus.PLAYER1, null);
        this.players[1] = new Player(CellStatus.PLAYER2, null);
        this.players[2] = new Player(CellStatus.PLAYER3, null);
        this.players[3] = new Player(CellStatus.PLAYER4, null);
        this.currentPlayer = this.players[0];
        //this.gameStatus = GameStatus.WAITING_FOR_PLAYER;
        this.creationDate = LocalDateTime.now();
    }

    public boolean isGameOver() {
        for (Player player : players) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    for (Block block : player.getInventory().getBlocks()) {
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

    public String addPlayer(String playerName) {

        // Find the next free slot in the players array
        int nextSlot = -1;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                nextSlot = i;
                break;
            } else if (players[i].getPlayerName() == null) {
                nextSlot = i;
                break;
            }
        }

        // Throw an exception if there are no free slots
        if (nextSlot == -1) {
            throw new RuntimeException("No free slots in the players array");
        }

        // Add the player to the next free slot
        if (nextSlot != -1) {
            players[nextSlot].setPlayerName(playerName);
        }

        return players[nextSlot].getPlayerId();
    }

    public String getId() {
        return gameId;
    }
    public Player getPlayerById(String playerId) {
        if (playerId == null) {
            throw new IllegalArgumentException("playerId cannot be null");
        }

        for (Player player : players) {
            if (player != null && player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null; // return null if no player with the specified playerId is found
    }

    public Player[] getPlayers() {
        return players;
    }


}
