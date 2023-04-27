package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.controller.GameHandler;
import ch.uzh.ifi.hase.soprafs23.entity.GameEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GameSSE {

    private final GameHandler processor;

    public GameSSE(GameHandler processor) {
        this.processor = processor;
    }

    @PostMapping("/gameboard-updates")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameEvent> send(@RequestBody GameEvent event) {
        processor.publish(event);
        return Mono.just(event);
    }

    @GetMapping(path = "/gameboard-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> consumer() {
        return Flux.create(sink -> processor.subscribe(sink::next)).map(
                event -> ServerSentEvent.builder().data(event).event("message").build());
    }
}
