package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block1;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameServiceTest {



    @Test
    public void placeBlock_Successful(){
        GameService gameService = new GameService();
        Game game = gameService.createGame();
        GameBoard gameBoard = game.getGameBoard();
        Player testPlayer = new Player(CellStatus.PLAYER1,"testPlayer");
        Block block1 = new Block1(testPlayer,CellStatus.PLAYER1);
        List<Block> unplacedBlocks = testPlayer.getInventory().getUnplayedBlocks();

        gameService.placeBlock(game,testPlayer,0,0,block1);

        assertTrue(CellStatus.PLAYER1 == gameBoard.getGameBoard()[0][0].getStatus());
        assertFalse(unplacedBlocks.contains(block1));
    }
}
