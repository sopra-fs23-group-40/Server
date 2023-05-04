package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block1;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        assertSame(CellStatus.PLAYER1, gameBoard.getGameBoard()[0][0].getStatus());
        assertFalse(unplacedBlocks.contains(block1));
    }

    @Test
    public void testGetGameById_Successful(){
        GameService gameService = new GameService();
        Game testGame = gameService.createGame();
        String gameId = testGame.getId();
        Game game = gameService.getGameById(gameId);
        assertSame(game, testGame);
    }

    @Test
    public void testGetGameById_InvalidId() {
        GameService gameService = new GameService();
        Game testGame = gameService.createGame();
        String invalidId = "non-existent-id";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gameService.getGameById(invalidId)
        );

        String expectedMessage = "Game with ID " + invalidId + " not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
