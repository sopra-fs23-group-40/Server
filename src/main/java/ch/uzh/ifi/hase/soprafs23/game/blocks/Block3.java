package ch.uzh.ifi.hase.soprafs23.game.blocks;
import ch.uzh.ifi.hase.soprafs23.game.Player;


/* Class for a block akin to:

🟫🟫🟫

*/


public class Block3 extends Block {
    public Block3(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(cellStatus)}};

        this.numberOfSquares = 3;
        this.length = 3;
        this.height = 1;
    }

    @Override
    public String getBlockName() {
        return "Block3";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
}