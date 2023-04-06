package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫
🟫🟫

*/


public class Block4 extends Block {
    public Block4(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 3;
        this.length = 2;
        this.height = 2;
    }

    @Override
    public String getBlockName() {
        return "Block4";
    }
}