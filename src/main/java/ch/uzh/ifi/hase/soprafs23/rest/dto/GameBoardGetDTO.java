package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;

import java.util.Arrays;

public class GameBoardGetDTO {
    private CellStatus[] gameBoard;

    public GameBoardGetDTO(GameBoard gameBoard) {
        // Flatten the 2D array to a 1D array
        this.gameBoard = Arrays.stream(gameBoard.getGameBoard())
                .flatMap(Arrays::stream)
                .map(Cell::getStatus)
                .toArray(CellStatus[]::new);
    }

    public CellStatus[] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(CellStatus[] gameBoard) {
        this.gameBoard = gameBoard;
    }
}
