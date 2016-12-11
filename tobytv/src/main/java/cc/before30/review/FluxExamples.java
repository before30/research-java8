package cc.before30.review;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by before30 on 11/12/2016.
 */
public class FluxExamples {
    public static void main(String[] args) throws InterruptedException {
        final Flux<Long> src1 = Flux.fromIterable(Iter::new)
                .publishOn(Schedulers.newSingle("src1"));

        final Flux<Long> src2 = Flux.fromIterable(Iter::new)
                .publishOn(Schedulers.newSingle("src2"));

        Flux.combineLatest(
                v-> Tuples.of((long)v[0],(long)v[1]), 1,
                src1, src2)
                .log()
                .take(10);


        Tuple3<Integer, Integer, Integer> tuples = Tuples.of(1, 2, 3);
        System.out.println(tuples);

        System.out.println(tuples.getT1());
        System.out.println(tuples.getT2());
        System.out.println(tuples.getT3());

        TimeUnit.SECONDS.sleep(10);
    }
}

class Iter implements Iterator<Long> {
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
