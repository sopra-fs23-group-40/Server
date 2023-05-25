package ch.uzh.ifi.hase.soprafs23.game;

import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Block> blocks;

    public Inventory(Player player) {
        this.blocks = new ArrayList<>();
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public void removeBlock(Block block) {
        this.blocks.remove(block);
    }

    public void clear() {
        this.blocks.clear();
    }

    public List<Block> getUnplayedBlocks() {
        List<Block> unplayedBlocks = new ArrayList<>();

        for (Block block : blocks) {
            if (!block.isPlayed()) {
                unplayedBlocks.add(block);
            }
        }
        return unplayedBlocks;
    }

    public Block getBlockByBlockName(String blockName) {
        for (Block block : blocks) {
            if (block.getBlockName().equals(blockName)) {
                return block;
            }
        }
        return null;
    }

    public int getPlacedBlocks(){
        return (21-blocks.size());
    }

    public int getPlacedTiles() {
        int tiles = 0;
        List<Block> unplayedBlocks = getBlocks();
        for (Block b : unplayedBlocks){
            tiles += b.getNumberOfSquares();
        }
        return 89 - tiles;
    }

    public int getUnplacedTiles(){
        int tiles = 0;
        List<Block> unplayedBlocks = getBlocks();
        for (Block b : unplayedBlocks){
            tiles += b.getNumberOfSquares();
        }
        return tiles;
    }

}