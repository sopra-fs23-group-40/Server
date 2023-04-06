package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫
🟫
🟫
🟫

*/


public class Block8 extends Block {
    public Block8(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus)},
            {new Cell(cellStatus)},
            {new Cell(cellStatus)},
            {new Cell(cellStatus)}
        };

        this.numberOfSquares = 4;
        this.length = 1;
        this.height = 4;
    }

    @Override
    public String getBlockName() {
        return "Block8";
    }
}