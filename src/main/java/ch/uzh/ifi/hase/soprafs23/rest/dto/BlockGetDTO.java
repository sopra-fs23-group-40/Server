package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;

import java.util.Arrays;

public class BlockGetDTO {
    private String blockName;
    private int length;
    private int height;

    private CellStatus[][] shape;

    public BlockGetDTO(String blockName, int length, int height, Cell[][] cells) {
        this.blockName = blockName;
        this.length = length;
        this.height = height;

        this.shape = new CellStatus[cells.length][cells[0].length];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                shape[i][j] = cells[i][j].getStatus();
            }
        }
    }

    public CellStatus[][] getShape() {
        return shape;
    }
}
