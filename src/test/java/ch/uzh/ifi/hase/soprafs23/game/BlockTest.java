package ch.uzh.ifi.hase.soprafs23.game;
import ch.uzh.ifi.hase.soprafs23.game.blocks.*;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
public class BlockTest {
    private Player player1 = new Player(CellStatus.PLAYER1, "TestName");
    private  CellStatus cellStatus = player1.getStatus();


    @Test
    void rotateClockwiseReturnsBlock() {
        Block block = new Block18(player1, cellStatus);

        Cell[][] expected = {
                 {new Cell(cellStatus), new Cell(cellStatus), new Cell(cellStatus)},
                 {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL)},
                 {new Cell(cellStatus), new Cell(CellStatus.NEUTRAL), new Cell(CellStatus.NEUTRAL)}
         };

        block.rotateClockwise();

        Cell[][] actual = block.getBlock();

        assertArrayEquals(expected, actual);

    }

}
