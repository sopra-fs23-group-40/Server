package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:


🟫
🟫🟫🟫🟫

*/


public class Block16 extends Block {
    public Block16(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 5;
        this.length = 4;
        this.height = 2;
    }

    @Override
    public String getBlockName() {
        return "Block16";
    }

    @Override
    public Cell[][] getBlock() {
        return shape;
    }
}