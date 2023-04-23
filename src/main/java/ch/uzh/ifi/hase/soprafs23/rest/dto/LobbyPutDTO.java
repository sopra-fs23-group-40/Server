package ch.uzh.ifi.hase.soprafs23.rest.dto;

public class LobbyPutDTO {
    private Long id;
    private String passcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
