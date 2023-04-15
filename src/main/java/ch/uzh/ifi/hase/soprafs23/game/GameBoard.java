package ch.uzh.ifi.hase.soprafs23.game;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;

public class GameBoard {
    private final int size = 20; // As all of the boards are 20x20
    private Cell[][] board;


    public GameBoard(int size){
        this.board = new Cell[size][size];
    }

    public GameBoard() {

	}

	public void buildBoard(){
        for (int row = 0; row < size; row++){
            for (int col=0; col < size; col++){
                board[row][col] = new Cell(row, col, CellStatus.NEUTRAL);
            }
        }
    }

    public void placeBlock(int row, int col, Block block){
        Cell[][] piece = block.getBlock();
        for (int i = 0; i < piece.length; i++){
            for (int j = 0; j < piece.length; j++) {
                board[row+i][col+j].setStatus(piece[i][j].getStatus());
            }
        }
    }

    public boolean canPlacePiece(int x, int y, Block block) {
        Cell[][] piece = block.getBlock();
        int length = block.getLength();
        int height = block.getHeight();
        //check if piece is outside the board
        if (x + block.getLength() > board.length || y + block.getHeight() > board.length) {
            return false;
        }
        //check that the piece is not overlapping with other pieces
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (piece[i][j].getStatus() != CellStatus.NEUTRAL &&
                        board[x + i][y + j].getStatus() != CellStatus.NEUTRAL) {
                    return false;
                }
            }
        }
        //check that the piece is not touching a piece of same status along an edge
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (piece[i][j].getStatus() == CellStatus.PLAYER1) {
                    //check the left edge
                    if (board[x-1][y].getStatus() == CellStatus.PLAYER1){return false;}
                    //check the right edge
                    if (board[x+1][y].getStatus() == CellStatus.PLAYER1){return false;}
                    //check the edge above
                    if (board[x][y+1].getStatus() == CellStatus.PLAYER1){return false;}
                    //check the edge below
                    if (board[x][y-1].getStatus() == CellStatus.PLAYER1){return false;}
                }
            }
        }
        //check that the piece touches a piece of the same status in a corner
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (piece[i][j].getStatus() == CellStatus.PLAYER1) {
                    // Check upper-left corner
                    if (i > 0 && j > 0 && board[x + i - 1][y + j - 1].getStatus() == CellStatus.PLAYER1) {
                        return true;
                    }
                    // Check upper-right corner
                    if (i < length - 1 && j > 0 && board[x + i + 1][y + j - 1].getStatus() == CellStatus.PLAYER1) {
                        return true;
                    }
                    // Check lower-left corner
                    if (i > 0 && j < height - 1 && board[x + i - 1][y + j + 1].getStatus() == CellStatus.PLAYER1) {
                        return true;
                    }
                    // Check lower-right corner
                    if (i < length - 1 && j < height - 1 && board[x + i + 1][y + j + 1].getStatus() == CellStatus.PLAYER1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
