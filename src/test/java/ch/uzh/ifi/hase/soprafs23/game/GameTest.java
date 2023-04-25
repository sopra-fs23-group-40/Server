package ch.uzh.ifi.hase.soprafs23.game;

import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game = new Game();
    Player player1 = new Player(CellStatus.PLAYER1, "TestName");
    @Test
    void isGameOverCannotPlaceReturnTrue(){
        GameBoard gameBoard = game.getGameBoard();
        for (int i = 0; i<20;i+=2){
            for (int j = 0; j<20;j+=2){
                gameBoard.placeBlock(player1,i,j,player1.getBlocks()[0]);
            }
        }
        for (int i = 1; i<20;i+=2){
            for (int j = 1; j<20;j+=2){
                gameBoard.placeBlock(player1,i,j,player1.getBlocks()[0]);
            }
        }
        gameBoard.placeBlock(player1,0,19,player1.getBlocks()[0]);
        gameBoard.placeBlock(player1,19,0,player1.getBlocks()[0]);
        assertTrue(game.isGameOver());
    }
    @Test
    void isGameOverCanPlaceReturnFalse(){
        GameBoard gameBoard = game.getGameBoard();
        gameBoard.placeBlock(player1,0,0,player1.getBlocks()[5]);
        assertFalse(game.isGameOver());
    }

}