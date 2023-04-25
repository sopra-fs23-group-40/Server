package ch.uzh.ifi.hase.soprafs23.game;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;

public class GameBoard {
    private final int size = 20;
    private Cell[][] board;

    public GameBoard() {
        this.board = new Cell[size][size];
        buildBoard();
	}

	private void buildBoard(){
        for (int row = 0; row < size; row++){
            for (int col=0; col < size; col++){
                board[row][col] = new Cell(CellStatus.NEUTRAL);
            }
        }
    }

    public Cell[][] getGameBoard() {
        return board;
    }

    public void placeBlock(Player player, int row, int col, Block block){
        Cell[][] piece = block.getBlock();
        for (int i = 0; i < block.getHeight(); i++){
            for (int j = 0; j < block.getLength(); j++) {
                board[row+i][col+j].setStatus(piece[i][j].getStatus());
            }
        }
    }

    public boolean canPlacePiece(int y, int x, Block block) {
        CellStatus status = block.getPlayer().getStatus();
        Cell[][] piece = block.getBlock();
        int length = block.getLength();
        int height = block.getHeight();
        //check if piece is outside the board
        if (x + length > board.length || y + height > board.length) {
            return false;
        }
        //check that the piece is not overlapping with other pieces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (piece[i][j].getStatus() != CellStatus.NEUTRAL &&
                        board[y + i][x + j].getStatus() != CellStatus.NEUTRAL) {
                    return false;
                }
            }
        }
        //check that the piece is not touching a piece of same status along an edge
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (piece[i][j].getStatus() == status) {
                    //check the left edge
                    if (x+j-1 >= 0 && board[y+i][x+j-1].getStatus() == status){return false;}
                    //check the right edge
                    if (x+j+1 < size && board[y+i][x+j+1].getStatus() == status){return false;}
                    //check the edge above
                    if (y+i-1 >= 0 && board[y+i-1][x+j].getStatus() == status){return false;}
                    //check the edge below
                    if (y+i+1 < size && board[y+i+1][x+j].getStatus() == status){return false;}
                }
            }
        }
        //check if the piece is a corner piece
        //upper-left corner of the board
        if (y == 0 && x == 0 && board[0][0].getStatus() == CellStatus.NEUTRAL){ return true;}
        //upper-right corner of the board
        else if (y == 0 && x+length == size && board[0][size-1].getStatus()== CellStatus.NEUTRAL) {return true;}
        //lower-left corner of the board
        else if (y+height == size && x == 0 && board[size-1][0].getStatus()== CellStatus.NEUTRAL) {return true;}
        //lower-right corner of the board
        else if (y+height == size && x+length == size && board[size-1][size-1].getStatus()== CellStatus.NEUTRAL) {return true;}

        //check that the piece touches a piece of the same status in a corner
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (piece[i][j].getStatus() == status) {
                    // Check upper-left corner
                    if (y-1 >= 0 && x-1 >= 0 && board[y+i-1][x+j-1].getStatus() == status) {
                        return true;
                    }
                    // Check upper-right corner
                    if (y-1 >= 0 && x+j+1 < size && board[y+i-1][x+j+1].getStatus() == status) {
                        return true;
                    }
                    // Check lower-left corner
                    if (y+i+1 < size && x-1 >= 0 && board[y+i+1][x+j-1].getStatus() == status) {
                        return true;
                    }
                    // Check lower-right corner
                    if (y+i+1 < size && x+j+1 < size && board[y+i+1][x+j+1].getStatus() == status) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
