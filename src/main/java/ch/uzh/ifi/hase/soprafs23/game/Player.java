package ch.uzh.ifi.hase.soprafs23.game;

import ch.uzh.ifi.hase.soprafs23.game.blocks.*;

public class Player {
    private Block[] blocks;
    private Inventory inventory;
    private final CellStatus status;

    public Player(CellStatus cellStatus) {
        // Constructor logic here
        this.status = cellStatus;
        this.inventory = new Inventory(this);
        this.blocks = new Block[] {
                new Block1(this, cellStatus),
                new Block2(this, cellStatus),
                new Block3(this, cellStatus),
                new Block4(this, cellStatus),
                new Block5(this, cellStatus),
                new Block6(this, cellStatus),
                new Block7(this, cellStatus),
                new Block8(this, cellStatus),
                new Block9(this, cellStatus),
                new Block10(this, cellStatus),
                new Block11(this, cellStatus),
                new Block12(this, cellStatus),
                new Block13(this, cellStatus),
                new Block14(this, cellStatus),
                new Block15(this, cellStatus),
                new Block16(this, cellStatus),
                new Block17(this, cellStatus),
                new Block18(this, cellStatus),
                new Block19(this, cellStatus),
                new Block20(this, cellStatus),
                new Block21(this, cellStatus)
        };

        // Add all the blocks of the player to the inventory
        for (Block block : blocks) {
            inventory.addBlock(block);
        }

    }
    public Block[] getBlocks() {
        return blocks;
    }

    public Inventory getInventory() {
        return inventory;
    }


    public CellStatus getStatus() {
        return status;
    }
}
