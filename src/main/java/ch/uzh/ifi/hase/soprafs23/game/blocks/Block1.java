package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫

*/


public class Block1 extends Block {
    public Block1(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus)}};

        this.numberOfSquares = 1;
        this.length = 1;
        this.height = 1;
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