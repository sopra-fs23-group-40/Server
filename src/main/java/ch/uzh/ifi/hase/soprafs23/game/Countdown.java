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
        this.timer = new Timer();
        start();
    }

    public void start() {
        secondsRemaining = 30;  // Initialize countdown to 30 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                }
                else {
                    if (listener != null) {
                        listener.onCountdownComplete();
                    }
                    secondsRemaining = 30;
                }
            }
        }, 0, 1000);
    }


        public void stop () {
            timer.cancel();
        }

        public void resetTime(){
            this.secondsRemaining = 30;
        }


        public interface CountdownListener {
            void onCountdownComplete();
        }
    }
