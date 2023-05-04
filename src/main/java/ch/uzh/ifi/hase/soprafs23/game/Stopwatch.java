package ch.uzh.ifi.hase.soprafs23.game;

public class Stopwatch {
    private final long nanoSecondsPerMinute = 60000000000L;

    private long startTime = 0;
    private long stopTime = 0;

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void stop() {
        this.stopTime = System.nanoTime();
    }

    public long getMinutes() {
        if (stopTime == 0) {
            throw new RuntimeException("The clock hasn't been stopped yet!");
        }
        long elapsedTime = stopTime - startTime;
        return elapsedTime / nanoSecondsPerMinute;
    }
}
