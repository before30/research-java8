package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import org.junit.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * User: before30 
 * Date: 2016. 12. 23.
 * Time: 오전 9:30
 */
public class Part07Errors {

	@Test
	public void monoWithValueInsteadOfError() {
		Mono<User> mono = betterCallSaulForBonguMono(Mono.error(new IllegalStateException()));
		StepVerifier.create(mono)
			.expectNext(User.SAUL)
			.expectComplete()
			.verify();

		mono = betterCallSaulForBonguMono(Mono.just(User.SKYLER));
		StepVerifier.create(mono)
			.expectNext(User.SKYLER)
			.expectComplete()
			.verify();
	}

	Mono<User> betterCallSaulForBonguMono(Mono<User> mono) {
		return mono.otherwiseReturn(User.SAUL);
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
		return flux.onErrorResumeWith(e -> Flux.just(User.SAUL, User.JESSE));
	}

	@Test
	public void handleCheckedException() {
		Flux<User> flux = capitalizeMany(Flux.just(User.SAUL, User.JESSE));
		StepVerifier.create(flux)
			.expectError(GetOutOfHereException.class)
			.verify();
	}

	Flux<User> capitalizeMany(Flux<User> flux) {
		return null;
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
