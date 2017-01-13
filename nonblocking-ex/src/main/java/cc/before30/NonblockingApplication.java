package cc.before30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NonblockingApplication {

	public static void main(String[] args) {
		System.setProperty("server.tomcat.max-threads", "1");
		SpringApplication.run(NonblockingApplication.class, args);
	}
}
