package cc.before30.review.literx;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

/**
 * User: before30 
 * Date: 2016. 12. 21.
 * Time: 오후 1:47
 */
public class Part01Flux {

	@Test
	public void empty() {
		Flux<String> flux = emptyFlux();
		StepVerifier.create(flux)
			.expectComplete()
			.verify();

	}

	Flux<String> emptyFlux() {
		return Flux.empty();
	}

	@Test
	public void fromValues() {
		Flux<String> flux = fooBarFluxFromValues();
		StepVerifier.create(flux)
			.expectNext("foo", "bar")
			.expectComplete()
			.verify();
	}

	Flux<String> fooBarFluxFromValues() {
		return Flux.fromIterable(Arrays.asList("foo", "bar"));
	}

	@Test
	public void error() {
		Flux<String> flux = errorFlux();
		StepVerifier.create(flux)
			.expectError(IllegalStateException.class)
			.verify();
	}

	Flux<String> errorFlux() {
		return Flux.error(new IllegalStateException());
	}

	@Test
	public void countEach100ms() {
		Flux<Long> flux = counter();
		StepVerifier.create(flux)
			.expectNext(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
			.expectComplete()
			.verify();
	}

	Flux<Long> counter() {
		return Flux.range(0, 10).map(Integer::toUnsignedLong).intervalMillis(100).take(10);
	}
}
