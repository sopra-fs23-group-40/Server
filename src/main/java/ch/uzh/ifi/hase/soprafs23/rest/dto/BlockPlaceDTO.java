package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class BlockPlaceDTO {
    private String blockName;
    private int row;
    private int column;
    private boolean[][] shape;

    public BlockPlaceDTO(String blockName, int row, int column, boolean[][] shape) {
        this.blockName = blockName;
        this.row = row;
        this.column = column;
        this.shape = shape;
    }

    public String getBlockName() {
        return blockName;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean[][] getShape() { return shape; }
}
