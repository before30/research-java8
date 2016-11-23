package cc.before30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	/***
	 * For Jar
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/***
	 * For War
	 */
	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoApplication.class);
	}

}

//@RestController
@Controller
class HelloController {
	@RequestMapping("/hi/{name}")
	public String hi(Map model, @PathVariable String name) {
		model.put("name", name);

		return "hello";
	}
}
