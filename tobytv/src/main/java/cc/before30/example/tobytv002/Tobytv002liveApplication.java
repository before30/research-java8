package cc.before30.example.tobytv002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by before30 on 2016. 11. 6..
 */
@SpringBootApplication
public class Tobytv002liveApplication {
    public static void main(String[] args) {
        SpringApplication.run(Tobytv002liveApplication.class, args);
    }

    @RestController
    public static class MyController {
        @RequestMapping("/")
        public List<User> users() {
            return Arrays.asList(new User("A"), new User("B"), new User("C"));
        }
    }

    public static class User {
        String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public User() {

        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
