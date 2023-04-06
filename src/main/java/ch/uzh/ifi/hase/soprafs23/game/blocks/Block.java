package ch.uzh.ifi.hase.soprafs23.game.blocks;

public interface Block {
    void rotate();
    int getLength();
    int getHeight();
    Cell[][] getBlock();
}
