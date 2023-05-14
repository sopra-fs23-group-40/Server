package ch.uzh.ifi.hase.soprafs23.rest.dto;


public class BlockPlaceDTO {
    private String blockName;
    private int row;
    private int column;
    private int rotation;
    private boolean flipped;

    public BlockPlaceDTO(String blockName, int row, int column, int rotation, boolean flipped) {
        this.blockName = blockName;
        this.row = row;
        this.column = column;
        this.rotation = rotation;
        this.flipped = flipped;
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

    public int getRotation() {
        return rotation;
    }

    public boolean isFlipped() {
        return flipped;
    }
}
