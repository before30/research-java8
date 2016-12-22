package cc.before30.review.literx.repository;

import cc.before30.review.literx.domain.User;
import lombok.Getter;
import reactor.core.publisher.Mono;

/**
 * User: before30 
 * Date: 2016. 12. 21.
 * Time: 오후 1:06
 */
public class BlockingUserRepository implements BlockingRepository<User> {
	private final ReactiveRepository<User> reactiveRepository;

	@Getter
	private int callCount = 0;

	public BlockingUserRepository() {
		reactiveRepository = new ReactiveUserRepository();
	}

	public BlockingUserRepository(long delayInMs) {
		reactiveRepository = new ReactiveUserRepository(delayInMs);
	}

	public BlockingUserRepository(User... users) {
		reactiveRepository = new ReactiveUserRepository(users);
	}

	public BlockingUserRepository(long delayInMs, User... users) {
		reactiveRepository = new ReactiveUserRepository(delayInMs, users);
	}

	@Override public void save(User user) {
		callCount++;
		reactiveRepository.save(Mono.just(user)).block();
	}

	@Override public User findFirst() {
		callCount++;
		return reactiveRepository.findFirst().block();
	}

	@Override public Iterable<User> findAll() {
		callCount++;
		return reactiveRepository.findAll().toIterable();
	}

	@Override public User findById(String id) {
		callCount++;
		return reactiveRepository.findById(id).block();
	}

}
