package cc.before30.review.literx;

import cc.before30.review.literx.domain.User;
import cc.before30.review.literx.repository.ReactiveRepository;
import cc.before30.review.literx.repository.ReactiveUserRepository;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.CompletableFuture;

import static io.reactivex.Observable.fromPublisher;

/**
 * User: before30 
 * Date: 2016. 12. 26.
 * Time: 오후 12:57
 */
public class Part09Adapt {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

	@Test
	public void adaptToFlowable() {
		Flux<User> flux = repository.findAll();
		Flowable<User> observable = fromFluxToFlowable(flux);
		StepVerifier.create(fromFlowableToFlux(observable))
			.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
			.expectComplete()
			.verify();
	}

	Flowable<User> fromFluxToFlowable(Flux<User> flux) {
		return Flowable.fromPublisher(flux);
	}

	Flux<User> fromFlowableToFlux(Flowable<User> flowable) {
		return Flux.from(flowable);
	}

	@Test
	public void adaptToObservable() {
		Flux<User> flux = repository.findAll();
		Observable<User> observable = fromFluxToObservable(flux);
		StepVerifier.create(fromObservableToFlux(observable))
			.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
			.expectComplete()
			.verify();
	}

	Observable<User> fromFluxToObservable(Flux<User> flux) {
		return Observable.fromPublisher(flux);
	}

	Flux<User> fromObservableToFlux(Observable<User> observable) {
		return Flux.from(observable.toFlowable(BackpressureStrategy.BUFFER));
	}

	@Test
	public void adaptToSingle() {
		Mono<User> mono = repository.findFirst();
		Single<User> single = fromMonoToSingle(mono);
		StepVerifier.create(fromSingleToMono(single))
			.expectNext(User.SKYLER)
			.expectComplete()
			.verify();
	}

	Single<User> fromMonoToSingle(Mono<User> mono) {
//		return Flowable.fromPublisher(mono).toObservable().firstOrError();
		return Single.fromPublisher(mono);
	}

	Mono<User> fromSingleToMono(Single<User> single) {
		return Mono.from(single.toFlowable());
	}

	@Test
	public void adaptToCompletableFuture() {
		Mono<User> mono = repository.findFirst();
		CompletableFuture<User> future = fromMonoToCompletableFuture(mono);
		StepVerifier.create(fromCompletableFutureToMono(future))
			.expectNext(User.SKYLER)
			.expectComplete()
			.verify();
	}

	CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
		return mono.toFuture();
	}

	Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
		return Mono.fromFuture(future);
	}
}
