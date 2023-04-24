package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Player;

import java.util.HashMap;
import java.util.Map;


public class GameService {

    private Map<String, Game> games = new HashMap<>();

    public Game createGame() {
        Game newGame = new Game();
        games.put(newGame.getId(), newGame);
        return newGame;
    }

    public void placeBlock(Game game, Player player, int row, int col, Block block) {
        GameBoard board = game.getGameBoard();

        // Place the block on the board
        board.placeBlock(player, row, col, block);

        // Remove the block from the player's inventory
        player.getInventory().removeBlock(block);
    }


    public Game getGameById(String gameId) {
        if (games.containsKey(gameId)) {
            return games.get(gameId);
        } else {
            throw new RuntimeException("Game with ID " + gameId + " not found.");
        }
    }
}
