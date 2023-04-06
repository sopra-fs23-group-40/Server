package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫🟫🟫🟫🟫

*/


public class Block19 extends Block {
    public Block19(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 5;
        this.length = 5;
        this.height = 1;
    }

    @Override
    public String getBlockName() {
        return "Block19";
    }
}