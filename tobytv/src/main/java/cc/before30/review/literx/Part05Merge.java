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
public class Part05Merge {
    final static User MARIE = new User("mschrader", "Marie", "Schrader");
    final static User MIKE = new User("mehrmantraut", "Mike", "Ehrmantraut");

    ReactiveRepository<User> repository1 = new ReactiveUserRepository(500);
    ReactiveRepository<User> repository2 = new ReactiveUserRepository(MARIE, MIKE);

    @Test
    public void mergeWithInterleave() {
        Flux<User> flux = mergeFluxWithInterleave(repository1.findAll(), repository2.findAll());
        StepVerifier.create(flux)
                .expectNext(MARIE, MIKE, User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .expectComplete()
                .verify();
    }

    Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
        return Flux.from(flux1).mergeWith(flux2);
    }

    @Test
    public void mergeWithNoInterleave() {
        Flux<User> flux = mergeFluxWithNoInterleave(repository1.findAll(), repository2.findAll());
        StepVerifier.create(flux)
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL, MARIE, MIKE)
                .expectComplete()
                .verify();
    }

    Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {

        return flux1.concatWith(flux2);
    }

    @Test
    public void multipleMonoToFlux() {
        Mono<User> skylerMono = repository1.findFirst();
        Mono<User> marieMono = repository2.findFirst();
        Flux<User> flux = createFluxFromMultipleMono(skylerMono, marieMono);

        StepVerifier.create(flux)
                .expectNext(User.SKYLER, MARIE)
                .expectComplete()
                .verify();
    }

    Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
        return Flux.concat(mono1, mono2);
//        return Flux.from(mono1).concatWith(mono2);
    }
}
