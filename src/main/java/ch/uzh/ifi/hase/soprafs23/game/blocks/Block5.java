package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫
🟫
🟫🟫

*/


public class Block5 extends Block {
    public Block5(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 4;
        this.length = 2;
        this.height = 3;
    }

    @Override
    public String getBlockName() {
        return "Block5";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
    
}