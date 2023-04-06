package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.LobbyType;

public class LobbyGetDTO {
    private String name;
    private LobbyType is_private;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LobbyType getIs_private() {
        return is_private;
    }

    public void setIs_private(LobbyType is_private) {
        this.is_private = is_private;
    }
}
