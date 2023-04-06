package ch.uzh.ifi.hase.soprafs23.service.blocks;

public interface Block {
    void rotate();
    int getLength();
    int getHeight();
    Cell[][] getBlock();
}
