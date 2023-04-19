package ch.uzh.ifi.hase.soprafs23.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STATISTICS")
public class Statistics implements Serializable {
    @Column(nullable = false)
    private int gamesPlayed;
    @Column(nullable = false)
    private int gamesWon;
    @Column(nullable = false)
    private int minutesPlayed;

    @Column(nullable = false)
    private Long userId;

    @Id
    @GeneratedValue
    private Long id;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int games_played) {
        this.gamesPlayed = games_played;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
