package cc.before30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CcserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcserverApplication.class, args);
	}
}
