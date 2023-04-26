package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.controller.LobbyHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SSE {

    private final LobbyHandler processor;

    public SSE(LobbyHandler processor) {
        this.processor = processor;
    }

    @PostMapping("/lobby-updates")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> send(@RequestBody String message) {
        processor.publish(message);
        return Mono.just(message);
    }

    @GetMapping(path = "/lobby-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> consumer() {
        return Flux.create(sink -> processor.subscribe(sink::next)).map(
                message -> ServerSentEvent.builder().data(message).event("message").build());
    }
}
