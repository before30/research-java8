package cc.before30.example.tobytv006;

import reactor.core.publisher.Flux;

/**
 * Created by before30 on 03/12/2016.
 */
public class ReactorEx {
    public static void main(String[] args) {
        Flux.<Integer>create(e -> {
            e.next(1);
            e.next(2);
            e.next(3);
            e.complete();
        })
        .log()
        .map(s -> s * 10)
        .log()
        .map(s -> s - 1)
        .log()
        .reduce(0, (a, b) -> a + b)
        .log()
        .subscribe(System.out::println);


    }
}
