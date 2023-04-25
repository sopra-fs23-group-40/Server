package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;

import java.util.Arrays;

public class GameBoardGetDTO {
    private CellStatus[][] gameBoard;

    public GameBoardGetDTO(GameBoard inputGameBoard) {
        Cell[][] cells = inputGameBoard.getGameBoard();
        this.gameBoard = new CellStatus[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                this.gameBoard[i][j] = cells[i][j].getStatus();
            }
        }
    }

    public CellStatus[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(CellStatus[][] gameBoard) {
        this.gameBoard = gameBoard;
    }
}