package cc.before30.example.tobytv002;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static cc.before30.examples.java8.toby002.Tobytv002liveApplication.*;

/**
 * Created by before30 on 2016. 11. 6..
 */

// Super Type Token

public class SpringTypeReference {
    public static void main(String[] args) {
//        ParameterizedTypeReference typeRef =
//                new ParameterizedTypeReference<List<Map<Set<Integer>, String>>>() {};
//        System.out.println(typeRef.getType());
        RestTemplate rt = new RestTemplate();
//        List<User> users = rt.getForObject("http://localhost:8080", List.class);
        List<User> users = rt.exchange("http://localhost:8080", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {}).getBody();
        System.out.println(users.get(0).getName());

        users.forEach(System.out::println);
    }
}
