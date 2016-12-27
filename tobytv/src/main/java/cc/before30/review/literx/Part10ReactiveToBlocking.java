package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import cc.before30.review.literx.repository.ReactiveRepository;
import cc.before30.review.literx.repository.ReactiveUserRepository;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class Part10ReactiveToBlocking {

    ReactiveRepository<User> repository = new ReactiveUserRepository();

    @Test
    public void mono() {
        Mono<User> mono = repository.findFirst();
        User user = monoToValue(mono);
        assertEquals(User.SKYLER, user);
    }

    User monoToValue(Mono<User> mono) {
        return mono.block();
    }

    @Test
    public void flux() {
        Flux<User> flux = repository.findAll();
        Iterable<User> users = fluxToValues(flux);
        Iterator<User> it = users.iterator();
        assertEquals(User.SKYLER, it.next());
        assertEquals(User.JESSE, it.next());
        assertEquals(User.WALTER, it.next());
        assertEquals(User.SAUL, it.next());
        assertFalse(it.hasNext());
    }

    Iterable<User> fluxToValues(Flux<User> flux) {
        return flux.toIterable();
    }
}
