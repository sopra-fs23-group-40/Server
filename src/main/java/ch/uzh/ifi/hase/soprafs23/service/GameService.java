package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Player;

public class GameService {

    public void placeBlock(Game game, Player player, int row, int col, Block block) {
        GameBoard board = game.getGameBoard();

        // Place the block on the board
        board.placeBlock(player, row, col, block);

        // Remove the block from the player's inventory
        player.getInventory().removeBlock(block);
    }
}
