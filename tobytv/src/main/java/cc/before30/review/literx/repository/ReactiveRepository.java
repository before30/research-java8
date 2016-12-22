package cc.before30.review.literx.repository;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User: before30 
 * Date: 2016. 12. 21.
 * Time: 오후 1:07
 */
public interface ReactiveRepository<T> {

	Mono<Void> save(Publisher<T> publisher);

	Mono<T> findFirst();

	Flux<T> findAll();

	Mono<T> findById(String id);
}
