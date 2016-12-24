package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import org.testng.annotations.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by before30 on 24/12/2016.
 */
public class Part07Errors {

    @Test
    public void monoWithValueInsteadOfError() {
        Mono<User> mono = betterCallSaulForBongusMono(Mono.error(new IllegalStateException()));
        StepVerifier.create(mono)
                .expectNext(User.SAUL)
                .expectComplete()
                .verify();

        mono = betterCallSaulForBongusMono(Mono.just(User.SKYLER));
        StepVerifier.create(mono)
                .expectNext(User.SKYLER)
                .expectComplete()
                .verify();

    }

    Mono<User> betterCallSaulForBongusMono(Mono<User> mono) {

        return mono.otherwise(e -> Mono.just(User.SAUL));
    }

    @Test
    public void fluxWithValueInsteadOfError() {
        Flux<User> flux = betterCallSaulAndJesseForBogusFlux(Flux.error(new IllegalStateException()));
        StepVerifier.create(flux)
                .expectNext(User.SAUL, User.JESSE)
                .expectComplete()
                .verify();

        flux = betterCallSaulAndJesseForBogusFlux(Flux.just(User.SKYLER, User.WALTER));
        StepVerifier.create(flux)
                .expectNext(User.SKYLER, User.WALTER)
                .expectComplete()
                .verify();

    }

    Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
        return flux.onErrorResumeWith(e -> Flux.fromIterable(Arrays.asList(User.SAUL, User.JESSE)));
    }

    @Test
    public void handleCheckedExceptions() {
        Flux<User> flux = capitalizeMany(Flux.just(User.SAUL, User.JESSE));
        StepVerifier.create(flux)
                .expectError(GetOutOfHereException.class)
                .verify();
    }

    Flux<User> capitalizeMany(Flux<User> flux) {

        return flux.map( u -> {
            try {
                return capitalizeUser(u);
            } catch (GetOutOfHereException e) {
                throw Exceptions.propagate(e);
            }
        });
    }

    User capitalizeUser(User user) throws GetOutOfHereException {
        if (user.equals(User.SAUL)) {
            throw new GetOutOfHereException();
        }
        return new User(user.getUsername(), user.getFirstname(), user.getLastname());
    }

    private class GetOutOfHereException extends Exception {
    }
}
