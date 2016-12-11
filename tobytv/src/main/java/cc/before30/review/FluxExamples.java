package cc.before30.review;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 11/12/2016.
 */
@Slf4j
public class FluxExamples {
    public static void main(String[] args) throws InterruptedException {
        //        Flux.range(1, 10)
//                .log()
//                .subscribe(System.out::println);

        Flux.range(1, 10)
                .log()
                .subscribe(System.out::println);

        final Flux<Long> numSrc = Flux.fromIterable(IterNum::new)
                                        .publishOn(Schedulers.newSingle("numSrc"));
        final Flux<Character> charSrc = Flux.fromIterable(IterChar::new)
                                            .publishOn(Schedulers.newSingle("charSrc"));

        Flux.combineLatest(numSrc, charSrc, (n, c) -> Tuples.of(n, c))
                .log()
                .subscribe(System.out::println);

//        TimeUnit.SECONDS.sleep(10);
    }
}

class IterChar implements Iterator<Character> {
    char charactor = 'a';

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Character next() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        charactor = (char)((++charactor) % ('z' - 'a') + 'a');
        return charactor;
    }
}

class IterNum implements Iterator<Long> {
    long count = 0;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Long next() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
        return count++;
    }
}
