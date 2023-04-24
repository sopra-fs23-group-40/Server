package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;

import java.util.Arrays;

public class BlockGetDTO {
    private String blockName;
    private int length;
    private int height;

    private CellStatus[] shape;

    public BlockGetDTO(String blockName, int length, int height, Cell[][] shape) {
        this.blockName = blockName;
        this.length = length;
        this.height = height;
        // Flatten the 2D array to a 1D array
        this.shape = Arrays.stream(shape)
                .flatMap(Arrays::stream)
                .map(Cell::getStatus)
                .toArray(CellStatus[]::new);
    }

    public CellStatus[] getShape() {
        return shape;
    }
}
