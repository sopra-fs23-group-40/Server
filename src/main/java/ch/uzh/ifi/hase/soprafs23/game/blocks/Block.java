package ch.uzh.ifi.hase.soprafs23.game.blocks;
import ch.uzh.ifi.hase.soprafs23.constant.RotationDirection;
import ch.uzh.ifi.hase.soprafs23.game.Player;

public abstract class Block {
    protected Player player;
    protected boolean played;
    protected CellStatus cellStatus;
    protected Cell[][] shape;
    protected int numberOfSquares; //maybe useful for end of the game
    protected int length;
    protected int height;

    public Block(Player player, CellStatus cellStatus) {
        this.player = player;
        this.played = false;
        this.cellStatus = cellStatus;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public Cell[][] getShape() {
        return shape;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumberOfSquares() {
        return numberOfSquares;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public abstract Cell[][] getBlock();

    public abstract String getBlockName();

    public void flipHorizontal() {
        Cell[][] newShape = new Cell[shape.length][shape[0].length];

        for(int i = 0; i < shape.length; i++) {
            newShape[i] = shape[shape.length - i - 1];
        }

        shape = newShape;
    }
    
    public void flipVertical() {
        Cell[][] newShape = new Cell[height][length];
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                newShape[i][j] = shape[height - i - 1][j];
            }
        }
    
        shape = newShape;
    }
    
    public void rotateClockwise() {
        int numRows = shape.length;
        int numCols = shape[0].length;
        Cell[][] newShape = new Cell[numCols][numRows];
    
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                newShape[j][numRows - i - 1] = shape[i][j];
            }
        }
    
        shape = newShape;
        int temp = length;
        length = height;
        height = temp;
    }
    public void rotate(RotationDirection rotationDirection) {
        int numRows = shape.length;
        int numCols = shape[0].length;
        Cell[][] newShape = new Cell[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(rotationDirection == RotationDirection.CLOCKWISE) {
                    newShape[j][numRows - i - 1] = shape[i][j];
                }
                else if (rotationDirection == RotationDirection.COUNTER_CLOCKWISE) {
                    newShape[numCols - j - 1][i] = shape[i][j];
                }
            }
        }


        shape = newShape;
        int temp = length;
        length = height;
        height = temp;
    }

    @Override
    public String toString() {
        return getBlockName();
    }
}

