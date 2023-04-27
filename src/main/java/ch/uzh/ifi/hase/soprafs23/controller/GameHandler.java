package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.GameEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class GameHandler {

    private final List<Consumer<GameEvent>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<GameEvent> listener) {
        listeners.add(listener);
    }

    public void publish(GameEvent event) {
        listeners.forEach(listener -> listener.accept(event));
    }
}