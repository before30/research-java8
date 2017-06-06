package cc.before30.ex.toby014;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by before30 on 06/06/2017.
 */
@SpringBootApplication
@Slf4j
@RestController
public class Tobytv014Application {


    @GetMapping("/event/{id}")
    Mono<Event> event(@PathVariable("id") long id) {
        log.info("hello : {}", id);
        return Mono.just(new Event(id, "event:" + id));
    }

    @GetMapping("/event2/{id}")
    Mono<List<Event>> event2(@PathVariable long id) {
        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        return Mono.just(list);
    }


    @GetMapping("/events")
    Flux<Event> events() {
        return Flux.just(new Event(1L, "event1"), new Event(2L, "event2"));
    }

    @GetMapping(value = "/events2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> events2() {
        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        return Flux.fromIterable(list);
    }

    @GetMapping(value = "/events3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> events3() {
        Stream<Event> stream = Stream.generate(() -> new Event(System.currentTimeMillis(), "value"));
        return Flux.fromStream(stream)
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    @GetMapping(value = "/event4", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> events4() {
        return Flux
                .<Event>generate(sink -> sink.next(new Event(System.currentTimeMillis(), "value")))
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    @GetMapping(value = "/events5", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> events5() {
        return Flux
                .<Event, Long>generate(() -> 1L, (id, sink) -> {
                    sink.next(new Event(id, "value" + id));
                    return id + 1;
                })
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    @GetMapping(value = "/events6", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> events6() {
        Flux<Event> es = Flux
                .<Event, Long>generate(() -> 1L, (id, sink) -> {
                    sink.next(new Event(id, "value" + id));
                    return id + 1;
                });
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(es, interval).map(tu -> tu.getT1()).take(10);
    }


        public static void main(String[] args) {
        SpringApplication.run(Tobytv014Application.class, args);
    }

    @Data
    @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }

}
