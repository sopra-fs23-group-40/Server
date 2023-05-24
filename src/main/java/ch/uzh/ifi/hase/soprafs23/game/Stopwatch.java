package ch.uzh.ifi.hase.soprafs23.game;

import java.util.Date;

public class Stopwatch {
    private final long nanoSecondsPerSecond = 1000000000L;
    private final long nanoSecondsPerMinute = 60000000000L;

    private long startTime = 0;
    private long stopTime = 0;
    private Date startDate;
    private final String notRunningError = "The clock hasn't been stopped yet!";

    public void start() {
        this.startTime = System.nanoTime();
        this.startDate = new Date();
    }

    public void stop() {
        this.stopTime = System.nanoTime();
    }

    public int getMinutes() {
        if (stopTime == 0) {
            throw new RuntimeException(notRunningError);
        }
        long elapsedTime = stopTime - startTime;
        return (int) Math.floor(elapsedTime / nanoSecondsPerMinute);
    }

    protected int getCurrentMinutes(){
        long elapsedTime = System.nanoTime() - startTime;
        return (int) Math.floor(elapsedTime / nanoSecondsPerMinute);
    }

    public Date getStartDate() {
        return startDate;
    }
}
