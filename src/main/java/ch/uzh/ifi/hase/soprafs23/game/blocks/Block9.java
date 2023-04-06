package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫🟫
🟫🟫

*/


public class Block9 extends Block {
    public Block9(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(cellStatus)},
            {new Cell(cellStatus), new Cell(cellStatus)},
        };

        this.numberOfSquares = 4;
        this.length = 2;
        this.height = 2;
    }

    @Override
    public String getBlockName() {
        return "Block9";
    }
}