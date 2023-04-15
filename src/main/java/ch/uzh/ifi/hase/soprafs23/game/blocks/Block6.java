package ch.uzh.ifi.hase.soprafs23.game.blocks;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Cell;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;


/* Class for a block akin to:

  🟫🟫
🟫🟫

*/


public class Block6 extends Block {
    public Block6(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(CellStatus.NEUTRAL), new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus),         new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)}
        };

        this.numberOfSquares = 4;
        this.length = 3;
        this.height = 2;
    }

    @Override
    public String getBlockName() {
        return "Block6";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }

}