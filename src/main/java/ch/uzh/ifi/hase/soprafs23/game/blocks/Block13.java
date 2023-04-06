package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

    🟫🟫
🟫🟫🟫

*/


public class Block13 extends Block {
    public Block13(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL), new Cell(cellStatus), new Cell(cellStatus)},
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)}
        };

        this.numberOfSquares = 5;
        this.length = 4;
        this.height = 2;
    }

    @Override
    public String getBlockName() {
        return "Block13";
    }
}