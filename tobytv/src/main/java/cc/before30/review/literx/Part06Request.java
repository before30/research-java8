package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import cc.before30.review.literx.repository.ReactiveRepository;
import cc.before30.review.literx.repository.ReactiveUserRepository;
import org.reactivestreams.Subscription;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Consumer;

/**
 * Created by before30 on 22/12/2016.
 */
public class Part06Request {

    ReactiveRepository<User> repository = new ReactiveUserRepository();

    @Test
    public void requestAll() {
        Flux<User> flux = repository.findAll();
        StepVerifier verifier = requestAllExpectFour(flux);
        verifier.verify();
    }

    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectNextCount(4)
                .expectComplete();
    }

    @Test
    public void experimentWithLog() {
        Flux<User> flux = fluxWithLog();
        StepVerifier.create(flux, 0)
                .thenRequest(1)
                .expectNextMatches(u -> true)
                .thenRequest(1)
                .expectNextMatches(u -> true)
                .thenRequest(2)
                .expectNextMatches(u -> true)
                .expectNextMatches(u -> true)
                .expectComplete()
                .verify();
    }

    Flux<User> fluxWithLog() {
        return repository.findAll().log();
    }

    @Test
    public void experimentWithDoOn() {
        Flux<User> flux = fluxWithDoOnPrintln();
        StepVerifier.create(flux)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    Flux<User> fluxWithDoOnPrintln() {
        return repository.findAll()
                .doOnSubscribe((s) -> System.out.println("Starring:"))
                .doOnNext(u -> System.out.println(u.getFirstname() + " " + u.getLastname()))
                .doOnComplete(() -> System.out.println("The end!"));
    }
}
