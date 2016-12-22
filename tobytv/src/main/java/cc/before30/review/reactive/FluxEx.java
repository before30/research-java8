package cc.before30.review.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * User: before30 
 * Date: 2016. 12. 21.
 * Time: 오전 11:50
 */
@Slf4j
public class FluxEx {
	public static void main(String[] args) {
//		Flux.just("red", "white", "blue")
//			.log()
//			.flatMap(value ->
//					Mono.just(value.toUpperCase())
//						.subscribeOn(Schedulers.parallel()),
//				2)
//			.subscribe(value -> {
//				log.info("Consumed: " + value);
//			});

		Flux.range(1, 10)
//			.log()
//			.map(FluxEx::intToString)
			.flatMap(value -> Mono.just(value)
				.publishOn(Schedulers.newParallel("mono", 5))
				.map(FluxEx::intToString)
				, 5
			)
//			.subscribeOn(Schedulers.parallel())
			.subscribe(value -> log.info("{}", value));

		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String intToString(int arg) {
		try {
			log.info("my thread is {}", Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return String.valueOf(arg);
	}

}
