package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.RotationDirection;

public class BlockRotateDTO {
    private String blockName;

    private RotationDirection rotationDirection;

    public BlockRotateDTO(String blockName, RotationDirection rotationDirection) {
        this.blockName = blockName;
        this.rotationDirection = rotationDirection;
    }
    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }


    public RotationDirection getRotationDirection() {
        return rotationDirection;
    }

    public void setRotationDirection(RotationDirection rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
}
