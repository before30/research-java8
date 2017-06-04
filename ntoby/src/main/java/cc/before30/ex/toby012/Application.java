package cc.before30.ex.toby012;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Created by before30 on 04/06/2017.
 */
@SpringBootApplication
@RestController
@Slf4j
@EnableAsync
public class Application {
    static final String URL1 = "http://localhost:8081/service?req={req}";
    static final String URL2 = "http://localhost:8081/service2?req={req}";

    @Autowired
    MyService myService;

    WebClient client = WebClient.create();

    @GetMapping("/rest")
    public Mono<String> rest(int idx) {
        Mono<ClientResponse> res = client.get().uri(URL1, idx).exchange();
        return res.flatMap(c -> c.bodyToMono(String.class));
    }

    @GetMapping("/rest2")
    public Mono<String> rest2(int idx) {
        return client.get().uri(URL1, idx).exchange() // mono<clientresponse>
                .flatMap(c -> c.bodyToMono(String.class)) // mono<string>
                .flatMap(res1 -> client.get().uri(URL2, res1).exchange()) // mono<clientresponse>
                .flatMap(c -> c.bodyToMono(String.class)) // mono<string>
                .doOnNext(c -> log.info(c))
                .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2))) // completablefuture -> mono<string>
                .doOnNext(c -> log.info(c));

    }

    @Service
    public static class MyService {
        @Async
       public CompletableFuture<String> work(String req) {
           return CompletableFuture.completedFuture(req + "/asyncwork");
       }
    }

    public static void main(String[] args) {
        System.setProperty("reactor.ipc.netty.workerCount", "2");
        System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
        SpringApplication.run(Application.class, args);

    }
}
