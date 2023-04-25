package ch.uzh.ifi.hase.soprafs23.game.blocks;
import ch.uzh.ifi.hase.soprafs23.game.Player;



/* Class for a block akin to:

🟫

*/


public class Block1 extends Block {
    public Block1(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.numberOfSquares = 1;
        this.length = 1;
        this.height = 1;

        this.shape = new Cell[][] {
            {new Cell(cellStatus)}};

        
    }

    @Override
    public String getBlockName() {
        return "Block1";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
}