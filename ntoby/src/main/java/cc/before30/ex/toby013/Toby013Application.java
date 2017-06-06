package cc.before30.ex.toby013;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by before30 on 05/06/2017.
 */
@SpringBootApplication
@RestController
@Slf4j
public class Toby013Application {

    @Autowired
    MyService myService;

    @GetMapping("/")
    Mono<String> hello() {
        log.info("pos1");
//        Mono m  = Mono.just("Hello WebFlux").doOnNext(c -> log.info(c)).log();
//        Mono m = Mono.just(myService.generateHello()).doOnNext(c -> log.info(c)).log();
        // Just를 쓰는 것은 동기로 하는 것과 같다
        /*
        2017-06-05 22:28:09.903  INFO 20665 --- [ctor-http-nio-2] c.b.ex.toby013.Toby013Application        : pos1
2017-06-05 22:28:09.903  INFO 20665 --- [ctor-http-nio-2] c.b.e.t.Toby013Application$MyService     : method generateHello
2017-06-05 22:28:09.905  INFO 20665 --- [ctor-http-nio-2] c.b.ex.toby013.Toby013Application        : pos2
2017-06-05 22:28:09.914  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | onSubscribe([Fuseable] FluxPeekFuseable.PeekFuseableSubscriber)
2017-06-05 22:28:09.916  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | request(1)
2017-06-05 22:28:09.916  INFO 20665 --- [ctor-http-nio-2] c.b.ex.toby013.Toby013Application        : Hello Mono
2017-06-05 22:28:09.916  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | onNext(Hello Mono)
2017-06-05 22:28:09.930  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | request(1)
2017-06-05 22:28:09.930  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | request(31)
2017-06-05 22:28:09.931  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | onComplete()
2017-06-05 22:28:10.471  INFO 20665 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | cancel()
         */

        Mono m = Mono.fromSupplier(myService::generateHello).doOnNext(c -> log.info(c)).log();
        /*
        2017-06-05 22:29:39.780  INFO 20766 --- [ctor-http-nio-2] c.b.ex.toby013.Toby013Application        : pos1
2017-06-05 22:29:39.783  INFO 20766 --- [ctor-http-nio-2] c.b.ex.toby013.Toby013Application        : pos2
2017-06-05 22:29:39.794  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | onSubscribe([Fuseable] FluxPeekFuseable.PeekFuseableSubscriber)
2017-06-05 22:29:39.795  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | request(1)
2017-06-05 22:29:39.795  INFO 20766 --- [ctor-http-nio-2] c.b.e.t.Toby013Application$MyService     : method generateHello
2017-06-05 22:29:39.795  INFO 20766 --- [ctor-http-nio-2] c.b.ex.toby013.Toby013Application        : Hello Mono
2017-06-05 22:29:39.795  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | onNext(Hello Mono)
2017-06-05 22:29:39.810  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | request(1)
2017-06-05 22:29:39.811  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | request(31)
2017-06-05 22:29:39.811  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | onComplete()
2017-06-05 22:29:41.803  INFO 20766 --- [ctor-http-nio-2] reactor.Mono.PeekFuseable.1              : | cancel()

         */
        log.info("pos2");
        // spring이 알아서 m.subscribe() 해준다
        // mono 나 flux는 여러개의 subscriber를 가질 수 있다
        // hot type(실시간 data), cold type(언제나 처음부터 다시, replay) 소스가 있는데
        m.subscribe();
        m.subscribe();
        m.subscribe();
        String msg2 = (String) m.block();
        // subscribe가 1번 실행된다.
        log.info("msg2 {}", msg2);
        return m;
    }

    public static void main(String[] args) {
        SpringApplication.run(Toby013Application.class, args);
    }

    @Service
    @Slf4j
    static class MyService {
        public String generateHello() {
            log.info("method generateHello");
            return "Hello Mono";
        }
    }
}
