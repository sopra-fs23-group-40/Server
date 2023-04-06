package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class LobbyGetDTO {
    private String name;
    private Boolean is_private;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }
}
