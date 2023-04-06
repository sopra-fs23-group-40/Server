package ch.uzh.ifi.hase.soprafs23.game.blocks;

public class ThreeCornerBlock implements Block{
    private int numberOfSquares = 3; //maybe useful for end of the game
    private final int length = 2;
    private final int height = 2;
    private Cell[][] block = new Cell[2][2];

    public ThreeCornerBlock(){
        for (int row = 0; row < length; row++){
            for (int col=0; col < height; col++){
                block[row][col] = new Cell(row, col, CellStatus.NEUTRAL);
            }
        }
        block[0][0].setStatus(CellStatus.PLAYER1);
        block[0][1].setStatus(CellStatus.NEUTRAL);
        block[1][0].setStatus(CellStatus.PLAYER1);
        block[1][1].setStatus(CellStatus.PLAYER1);

    }
    @Override
    public void rotate() {

    }
    @Override
    public int getLength(){
        return length;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Cell[][] getBlock(){
        return block;
    }
}
