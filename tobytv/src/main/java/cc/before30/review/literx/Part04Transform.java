package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import cc.before30.review.literx.repository.ReactiveRepository;
import cc.before30.review.literx.repository.ReactiveUserRepository;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Created by before30 on 22/12/2016.
 */
public class Part04Transform {

    ReactiveRepository<User> repository = new ReactiveUserRepository();

    @Test
    public void transformMono() {
        Mono<User> mono = repository.findFirst();
        StepVerifier.create(capitalizeOne(mono))
                .expectNext(new User("SWHITE", "SKYLER", "WHITE"))
                .expectComplete()
                .verify();
    }

    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(value -> new User(value.getUsername().toUpperCase(),
                value.getFirstname().toUpperCase(),
                value.getLastname().toUpperCase()));
    }

    @Test
    public void transformFlux() {
        Flux<User> flux = repository.findAll();
        StepVerifier.create(capitalizeMany(flux))
                .expectNext(
                        new User("SWHITE", "SKYLER", "WHITE"),
                        new User("JPINKMAN", "JESSE", "PINKMAN"),
                        new User("WWHITE", "WALTER", "WHITE"),
                        new User("SGOODMAN", "SAUL", "GOODMAN")
                ).expectComplete()
                .verify();
    }

    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(
                value -> new User(value.getUsername().toUpperCase(), value.getFirstname().toUpperCase(), value.getLastname().toUpperCase())
        );
    }

    @Test
    public void asyncTransformFlux() {
        Flux<User> flux = repository.findAll();
        StepVerifier.create(asyncCapitalizeMany(flux))
                .expectNext(
                        new User("SWHITE", "SKYLER", "WHITE"),
                        new User("JPINKMAN", "JESSE", "PINKMAN"),
                        new User("WWHITE", "WALTER", "WHITE"),
                        new User("SGOODMAN", "SAUL", "GOODMAN")
                ).expectComplete()
                .verify();
    }

    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(user -> asyncCapitalizeUser(user));
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
    }
}
