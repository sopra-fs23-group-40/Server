package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

🟫
🟫🟫  
  🟫🟫

*/


public class Block20 extends Block {
    public Block20(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(CellStatus.NEUTRAL), new Cell(cellStatus), new Cell(cellStatus)}
        };

        this.numberOfSquares = 5;
        this.length = 3;
        this.height = 3;
    }

    @Override
    public String getBlockName() {
        return "Block20";
    }
}