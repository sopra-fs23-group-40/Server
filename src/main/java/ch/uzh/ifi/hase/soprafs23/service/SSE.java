package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.controller.LobbyHandler;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
public class SSE {

    private final LobbyHandler processor;

    public SSE(LobbyHandler processor) {
        this.processor = processor;
    }

    @PostMapping("/lobby-updates")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LobbyEvent> send(@RequestBody LobbyEvent event) {
        processor.publish(event);
        return Mono.just(event);
    }

    @GetMapping(path = "/lobby-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> consumer() {
        return Flux.create(sink -> processor.subscribe(sink::next)).map(
                event -> ServerSentEvent.builder().data(event).event("message").build());
    }
}
