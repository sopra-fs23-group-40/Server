package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class BlockFlipDTO {

    private String blockName;
    private Boolean vertical;


    public BlockFlipDTO(String blockName, Boolean vertical) {
        this.blockName = blockName;
        this.vertical = vertical;
    }
    public String getBlockName() {
        return blockName;
    }

    public Boolean getVertical() {
        return vertical;
    }


}
