package cc.before30.review.literx.repository;

/**
 * User: before30 
 * Date: 2016. 12. 21.
 * Time: 오후 1:05
 */
public interface BlockingRepository<T>{
	void save(T value);

	T findFirst();

	Iterable<T> findAll();

	T findById(String id);
}
