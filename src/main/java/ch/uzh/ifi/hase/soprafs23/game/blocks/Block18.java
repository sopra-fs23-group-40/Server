package ch.uzh.ifi.hase.soprafs23.game.blocks;
import ch.uzh.ifi.hase.soprafs23.game.Player;



/* Class for a block akin to:

🟫
🟫  
🟫🟫🟫

*/


public class Block18 extends Block {
    public Block18(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 5;
        this.length = 3;
        this.height = 3;
    }

    @Override
    public String getBlockName() {
        return "Block18";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
}