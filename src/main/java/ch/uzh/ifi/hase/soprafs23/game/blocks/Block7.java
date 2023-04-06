package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫
🟫🟫
🟫

*/


public class Block7 extends Block {
    public Block7(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus),         new Cell(cellStatus)},
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)}
        };

        this.numberOfSquares = 4;
        this.length = 2;
        this.height = 3;
    }

    @Override
    public String getBlockName() {
        return "Block7";
    }
}