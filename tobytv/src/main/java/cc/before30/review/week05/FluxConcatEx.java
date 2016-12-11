package cc.before30.review.week05;

import reactor.core.publisher.Flux;

import java.util.Iterator;

/**
 * Created by before30 on 12/12/2016.
 */
public class FluxConcatEx {

    public static void main(String[] args) {
        final Flux<Integer> intSrc1 = Flux.range(0, 10);
        final Flux<Integer> intSrc2 = Flux.range(100, 10);

        Flux.concat(intSrc1, intSrc2)
            .log()
            .subscribe(System.out::println);

        final Flux<Integer> intSrc3 = Flux.fromIterable(IntStreamWithStart::new);

//        Flux.concat(intSrc3, intSrc2)
//                .log()
//                .subscribe(System.out::println);


    }
}

class IntStreamWithStart implements Iterator<Integer> {
    private int num;

    public IntStreamWithStart() {
        this.num = 0;
    }

    public IntStreamWithStart(int init) {
        this.num = init;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        return num++;
    }
}
