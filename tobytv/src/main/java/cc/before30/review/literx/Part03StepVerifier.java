package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.Assert.fail;

/**
 * User: before30 
 * Date: 2016. 12. 22.
 * Time: 오후 12:29
 */
public class Part03StepVerifier {

	@Test
	public void expectElementsThenComplete() {
		expectFooBarComplete(Flux.just("foo", "bar"));
	}

	void expectFooBarComplete(Flux<String> flux) {
		StepVerifier.create(flux)
			.expectNext("foo", "bar")
			.expectComplete()
			.verify();
//		fail();
	}

	@Test
	public void expect2ElementsThenError() {
		expectFooBarError(Flux.just("foo", "bar").concatWith(Mono.error(new RuntimeException())));
	}

	void expectFooBarError(Flux<String> flux) {
		StepVerifier.create(flux)
			.expectNext("foo", "bar")
			.expectError(RuntimeException.class)
			.verify();
	}

	@Test
	public void expectElementsWithThenComplete() {
		expectSkylerJesseComplete(
			Flux.just(new User("swhite", null, null), new User("jpinkman", null, null))
		);
	}

	void expectSkylerJesseComplete(Flux<User> flux) {
		StepVerifier.create(flux)
			.expectNextMatches(u -> u.getUsername().equals("swhite"))
			.expectNextMatches(u -> u.getUsername().equals("jpinkman"))
			.expectComplete()
			.verify();
	}

	@Test
	public void count() {
		expect10Elements(Flux.interval(Duration.ofSeconds(1)).take(10));
	}

	void expect10Elements(Flux<Long> flux) {
		StepVerifier.create(flux)
			.expectNextCount(10)
			.expectComplete()
			.verify();
	}

	@Test
	public void countWithVirtualTime() {
		expect3600Elements(() -> Flux.interval(Duration.ofSeconds(1)).take(3600));
	}

	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(supplier)
			.thenAwait(Duration.ofSeconds(3600))
			.expectNextCount(3600)
			.expectComplete()
			.verify();
	}
}
