package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class LobbyHandler {

    private final List<Consumer<LobbyEvent>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<LobbyEvent> listener) {
        listeners.add(listener);
    }

    public void publish(LobbyEvent event) {
        listeners.forEach(listener -> listener.accept(event));
    }
}