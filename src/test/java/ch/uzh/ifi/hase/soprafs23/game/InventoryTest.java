package ch.uzh.ifi.hase.soprafs23.game;
import ch.uzh.ifi.hase.soprafs23.game.blocks.*;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class InventoryTest {
    private Game game = new Game();
    private Player player1 = new Player(CellStatus.PLAYER1, "TestName");

    private Inventory inventory = player1.getInventory();

    @Test
    void getBlockByBlockNameReturnBlock(){
        Block expectedBlock = new Block18(player1, CellStatus.PLAYER1); // Create the expected block instance
        Block actualBlock = inventory.getBlockByBlockName("Block18");

        assertEquals(expectedBlock.getBlockName(), actualBlock.getBlockName());
        assertEquals(expectedBlock.getLength(), actualBlock.getLength());
        assertEquals(expectedBlock.getHeight(), actualBlock.getHeight());
    }

    @Test
    void getUnplayedBlocksReturnListOfBlocks(){
        Inventory inventory = player1.getInventory();
        CellStatus cellStatus = player1.getStatus();

        GameBoard gameBoard = game.getGameBoard();
        gameBoard.placeBlock(player1,0,19,inventory.getBlockByBlockName("Block5"));
        List<Block> actualBlocks = inventory.getUnplayedBlocks();
        List<Block> expectedBlocks = Arrays.asList(
                new Block1(player1, cellStatus),
                new Block2(player1, cellStatus),
                new Block3(player1, cellStatus),
                new Block4(player1, cellStatus),
                new Block6(player1, cellStatus),
                new Block7(player1, cellStatus),
                new Block8(player1, cellStatus),
                new Block9(player1, cellStatus),
                new Block10(player1, cellStatus),
                new Block11(player1, cellStatus),
                new Block12(player1, cellStatus),
                new Block13(player1, cellStatus),
                new Block14(player1, cellStatus),
                new Block15(player1, cellStatus),
                new Block16(player1, cellStatus),
                new Block17(player1, cellStatus),
                new Block18(player1, cellStatus),
                new Block19(player1, cellStatus),
                new Block20(player1, cellStatus),
                new Block21(player1, cellStatus)
        );

        for (int i = 0; i < 20; i++) {
            assertEquals(expectedBlocks.get(i).getBlockName(), actualBlocks.get(i).getBlockName());
        }
    }

}

