package ch.uzh.ifi.hase.soprafs23.entity;

public class LobbyEvent {
    private String message;
    private long id;

    public LobbyEvent(String message, long id) {
        this.message = message;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
