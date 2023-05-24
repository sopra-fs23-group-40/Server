package ch.uzh.ifi.hase.soprafs23.game;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
    private int secondsRemaining;
    private Timer timer;
    private CountdownListener listener;

    public Countdown(CountdownListener listener) {
        this.secondsRemaining = 30;
        this.listener = listener;
    }

    public void start() {
        secondsRemaining = 30;  // Initialize countdown to 30 seconds
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                } else {
                    stop();
                    if (listener != null) {
                        listener.onCountdownComplete();
                    }
                }
            }
        }, 0, 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public interface CountdownListener {
        void onCountdownComplete();
    }
}

