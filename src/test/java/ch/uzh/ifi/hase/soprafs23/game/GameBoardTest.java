package ch.uzh.ifi.hase.soprafs23.game;

import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private GameBoard gameBoard;

    @BeforeEach
    public void setup() {
        this.player1 = new Player(CellStatus.PLAYER1, null);
        this.player2 = new Player(CellStatus.PLAYER2, null);
        this.player3 = new Player(CellStatus.PLAYER3, null);
        this.player4 = new Player(CellStatus.PLAYER4, null);
        this.gameBoard = new GameBoard();
    }



    @Test
    void placeBlock() {
        gameBoard.placeBlock(player1, 0, 0, player1.getBlocks()[1]);
        assert (gameBoard.getGameBoard()[0][0].getStatus() == CellStatus.PLAYER1);
    }

    @Test
    void canPlacePieceNeutralCornersReturnTrue() {
        assertAll(() -> assertTrue(gameBoard.canPlacePiece(0, 0, player1.getBlocks()[1])),
                () -> assertTrue(gameBoard.canPlacePiece(0, 18, player2.getBlocks()[1])),
                () -> assertTrue(gameBoard.canPlacePiece(19, 0, player3.getBlocks()[1])),
                () -> assertTrue(gameBoard.canPlacePiece(19, 18, player4.getBlocks()[1])));
    }

    @Test
    void canPlacePieceNeutralCorners_LShape_ReturnFalse() {
        assertAll(() -> assertFalse(gameBoard.canPlacePiece(0, 0, player1.getBlocks()[5])),
                () -> assertFalse(gameBoard.canPlacePiece(0, 18, player1.getBlocks()[3])),
                () -> assertFalse(gameBoard.canPlacePiece(17, 0, player1.getBlocks()[11])),
                () -> assertFalse(gameBoard.canPlacePiece(17, 18, player1.getBlocks()[6])));
    }

    @Test
    void canPlacePiecePlayerStatusReturnFalse() {
        gameBoard.placeBlock(player1, 0, 0, player1.getBlocks()[1]);
        gameBoard.placeBlock(player1, 0, 18, player2.getBlocks()[1]);
        gameBoard.placeBlock(player1, 19, 0, player3.getBlocks()[1]);
        gameBoard.placeBlock(player1, 19, 18, player4.getBlocks()[1]);
        assertAll(() -> assertFalse(gameBoard.canPlacePiece(0, 0, player1.getBlocks()[1])),
                () -> assertFalse(gameBoard.canPlacePiece(0, 18, player2.getBlocks()[1])),
                () -> assertFalse(gameBoard.canPlacePiece(19, 0, player3.getBlocks()[1])),
                () -> assertFalse(gameBoard.canPlacePiece(19, 18, player4.getBlocks()[1])));
    }
    @Test
    void canPlacePieceTouchCornerReturnTrue() {
        gameBoard.placeBlock(player1, 0, 0, player1.getBlocks()[1]);
        gameBoard.placeBlock(player1, 0, 18, player2.getBlocks()[1]);
        gameBoard.placeBlock(player1, 19, 0, player3.getBlocks()[1]);
        gameBoard.placeBlock(player1, 19, 18, player4.getBlocks()[1]);
        assertAll(() -> assertTrue(gameBoard.canPlacePiece(1, 2, player1.getBlocks()[1])),
                () -> assertTrue(gameBoard.canPlacePiece(1, 16, player2.getBlocks()[1])),
                () -> assertTrue(gameBoard.canPlacePiece(18, 2, player3.getBlocks()[1])),
                () -> assertTrue(gameBoard.canPlacePiece(18, 16, player4.getBlocks()[1])));
    }
    @Test
    void canPlacePieceNotTouchingReturnFalse() {
        gameBoard.placeBlock(player1, 0, 0, player1.getBlocks()[1]);
        assertFalse(gameBoard.canPlacePiece(3, 3, player1.getBlocks()[1]));
    }
    @Test
    void canPlacePieceOverlappingReturnFalse(){
        gameBoard.placeBlock(player1, 0, 0, player1.getBlocks()[4]);
        assertFalse(gameBoard.canPlacePiece(1,0,player1.getBlocks()[1]));
    }
    @Test
    void canPlacePieceTouchingEdgeReturnFalse() {
        gameBoard.placeBlock(player1, 0, 0, player1.getBlocks()[1]);
        gameBoard.placeBlock(player1, 0, 18, player2.getBlocks()[1]);
        gameBoard.placeBlock(player1, 19, 0, player3.getBlocks()[1]);
        gameBoard.placeBlock(player1, 19, 18, player4.getBlocks()[1]);
        assertAll(() -> assertFalse(gameBoard.canPlacePiece(1, 1, player1.getBlocks()[1])),//above
                () -> assertFalse(gameBoard.canPlacePiece(0, 16, player2.getBlocks()[1])),//right
                () -> assertFalse(gameBoard.canPlacePiece(19, 2, player3.getBlocks()[1])),//left
                () -> assertFalse(gameBoard.canPlacePiece(18, 17, player4.getBlocks()[1])));//below
    }
    @Test
    void canPlacePieceOutsideReturnFalse(){
        assertFalse(gameBoard.canPlacePiece(19,19,player1.getBlocks()[1]));
    }
}