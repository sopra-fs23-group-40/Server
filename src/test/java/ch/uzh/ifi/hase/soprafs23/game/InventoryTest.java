package ch.uzh.ifi.hase.soprafs23.game;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block18;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Game game = new Game();
    Player player1 = new Player(CellStatus.PLAYER1, "TestName");

    @Test
    void getBlockByBlockNameReturnBlock(){
        Inventory inventory = player1.getInventory();
        Block expectedBlock = new Block18(player1, CellStatus.PLAYER1); // Create the expected block instance
        Block actualBlock = inventory.getBlockByBlockName("Block18");

        assertEquals(expectedBlock.getBlockName(), actualBlock.getBlockName());
        assertEquals(expectedBlock.getLength(), actualBlock.getLength());
        assertEquals(expectedBlock.getHeight(), actualBlock.getHeight());


    }
}
