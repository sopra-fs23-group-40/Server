package ch.uzh.ifi.hase.soprafs23.game.blocks;
import ch.uzh.ifi.hase.soprafs23.game.Player;



/* Class for a block akin to:


🟫  🟫
🟫🟫🟫

*/


public class Block17 extends Block {
    public Block17(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(cellStatus)},
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 5;
        this.length = 3;
        this.height = 2;
    }

    @Override
    public String getBlockName() {
        return "Block17";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
}