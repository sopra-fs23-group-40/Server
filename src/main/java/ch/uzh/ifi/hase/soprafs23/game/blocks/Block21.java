package ch.uzh.ifi.hase.soprafs23.game.blocks;



/* Class for a block akin to:

  🟫
🟫🟫🟫  
  🟫

*/


public class Block21 extends Block {
    public Block21(Player player, CellStatus cellStatus) {
        super(player, cellStatus);

        this.shape = new Cell[][] {
            {new Cell(CellStatus.NEUTRAL), new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)},
            {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus)},
            {new Cell(CellStatus.NEUTRAL), new Cell(cellStatus), new Cell(CellStatus.NEUTRAL)}
        };

        this.numberOfSquares = 5;
        this.length = 3;
        this.height = 3;
    }

    @Override
    public String getBlockName() {
        return "Block21";
    }
}