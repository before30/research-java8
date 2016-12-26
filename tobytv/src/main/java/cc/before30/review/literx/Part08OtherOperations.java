package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import cc.before30.review.literx.repository.ReactiveRepository;
import cc.before30.review.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * User: before30 
 * Date: 2016. 12. 26.
 * Time: 오후 12:33
 */
public class Part08OtherOperations {


	final static User MARIE = new User("mschrader", "Marie", "Schrader");
	final static User MIKE = new User("mehrmantraut", "Mike", "Ehrmantraut");

	@Test
	public void zipFirstNameAndLastName() {

		Flux<String> usernameFlux = Flux.just(User.SKYLER.getUsername(), User.JESSE.getUsername(), User.WALTER.getUsername(), User.SAUL.getUsername());
		Flux<String> firstnameFlux = Flux.just(User.SKYLER.getFirstname(), User.JESSE.getFirstname(), User.WALTER.getFirstname(), User.SAUL.getFirstname());
		Flux<String> lastnameFlux = Flux.just(User.SKYLER.getLastname(), User.JESSE.getLastname(), User.WALTER.getLastname(), User.SAUL.getLastname());

		Flux<User> userFlux = userFluxFromStringFlux(usernameFlux, firstnameFlux, lastnameFlux);
		StepVerifier.create(userFlux)
			.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
			.expectComplete()
			.verify();
	}

	Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux,
		Flux<String> firstnameFlux,
		Flux<String> lastnameFlux) {
//		return usernameFlux.zipWith(firstnameFlux).zipWith(lastnameFlux).map(tuple -> {
//			final String lastname = tuple.getT2();
//			final String firstname = tuple.getT1().getT2();
//			final String username = tuple.getT1().getT1();
//			User user = new User(username, firstname, lastname);
//			return user;
//		});

		return Flux
			.zip(usernameFlux, firstnameFlux, lastnameFlux)
			.map(tuple -> new User(tuple.getT1(), tuple.getT2(), tuple.getT3()));
	}

	@Test
	public void fastestMono() {
		ReactiveRepository<User> repository1 = new ReactiveUserRepository(MARIE);
		ReactiveRepository<User> repository2 = new ReactiveUserRepository(250, MIKE);

		Mono<User> mono = useFastestMono(repository1.findFirst(), repository2.findFirst());
		StepVerifier.create(mono)
			.expectNext(MARIE)
			.expectComplete()
			.verify();

		repository1 = new ReactiveUserRepository(250, MARIE);
		repository2 = new ReactiveUserRepository(MIKE);
		mono = useFastestMono(repository1.findFirst(), repository2.findFirst());
		StepVerifier.create(mono)
			.expectNext(MIKE)
			.expectComplete()
			.verify();
	}

	Mono<User> useFastestMono(Mono<User> mono1, Mono<User> mono2) {
		return Mono.first(mono1, mono2);
	}

	@Test
	public void fastestFlux() {
		ReactiveRepository<User> repository1 = new ReactiveUserRepository(MARIE, MIKE);
		ReactiveRepository<User> repository2 = new ReactiveUserRepository(250);
		Flux<User> flux = useFastestFlux(repository1.findAll(), repository2.findAll());
		StepVerifier.create(flux)
			.expectNext(MARIE, MIKE)
			.expectComplete()
			.verify();

		repository1 = new ReactiveUserRepository(250, MARIE, MIKE);
		repository2 = new ReactiveUserRepository();
		flux = useFastestFlux(repository1.findAll(), repository2.findAll());
		StepVerifier.create(flux)
			.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
			.expectComplete()
			.verify();
	}

	Flux<User> useFastestFlux(Flux<User> flux1, Flux<User> flux2) {
		return Flux.firstEmitting(flux1, flux2);
	}

	@Test
	public void complete() {
		ReactiveRepository<User> repository = new ReactiveUserRepository();
		Mono<Void> completion = fluxCompletion(repository.findAll());
		StepVerifier.create(completion)
			.expectComplete()
			.verify();
	}

	Mono<Void> fluxCompletion(Flux<User> flux) {

		return flux.then();
	}

	@Test
	public void nullHandling() {
		Mono<User> mono = nullAwareUserToMono(User.SKYLER);
		StepVerifier.create(mono)
			.expectNext(User.SKYLER)
			.expectComplete()
			.verify();

		mono = nullAwareUserToMono(null);
		StepVerifier.create(mono)
			.expectComplete()
			.verify();
	}

	Mono<User> nullAwareUserToMono(User user) {

		return Mono.justOrEmpty(user);
//		if (user == null) {
//			return Mono.empty();
//		} else {
//			return Mono.just(user);
//		}
	}

	@Test
	public void emptyHandling() {
		Mono<User> mono = emptyToSkyler(Mono.just(User.WALTER));
		StepVerifier.create(mono)
			.expectNext(User.WALTER)
			.expectComplete()
			.verify();
		mono = emptyToSkyler(Mono.empty());
		StepVerifier.create(mono)
			.expectNext(User.SKYLER)
			.expectComplete()
			.verify();
	}

	Mono<User> emptyToSkyler(Mono<User> mono) {
		return mono.defaultIfEmpty(User.SKYLER);
	}

}
