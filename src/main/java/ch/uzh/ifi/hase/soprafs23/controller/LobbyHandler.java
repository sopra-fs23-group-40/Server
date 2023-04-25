package ch.uzh.ifi.hase.soprafs23.controller;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class LobbyHandler {

    private final List<Consumer<String>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<String> listener) {
        listeners.add(listener);
    }

    public void publish(String message) {
        listeners.forEach(listener -> listener.accept(message));
    }
}