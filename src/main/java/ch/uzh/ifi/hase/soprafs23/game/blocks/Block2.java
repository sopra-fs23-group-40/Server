package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫🟫

*/


public class Block2 extends Block {
    public Block2(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(cellStatus)}};

        this.numberOfSquares = 1;
        this.length = 1;
        this.height = 1;
    }

    @Override
    public String getBlockName() {
        return "Block2";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
}