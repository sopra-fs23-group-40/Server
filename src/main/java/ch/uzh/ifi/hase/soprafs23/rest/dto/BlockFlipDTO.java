package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class BlockFlipDTO {

    private String blockName;


    public BlockFlipDTO(String blockName, Boolean vertical) {
        this.blockName = blockName;
    }
    public String getBlockName() {
        return blockName;
    }



}
