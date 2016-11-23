package cc.before30;

import cc.before30.domain.Player;
import cc.before30.domain.Team;
import cc.before30.domain.dao.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	@Autowired
	TeamDao teamDao;

	@PostConstruct
	public void init() {
		Set<Player> players = new HashSet<>();
		players.add(new Player("Charlie Brown", "pitcher"));
		players.add(new Player("Snoopy", "shortstop"));

		Team team = new Team("California", "Peanuts", players);
		teamDao.save(team);

	}
}

@RestController
class HelloController {
//	private Team team;
//
//	@PostConstruct
//	public void init() {
//		Set<Player> players = new HashSet<>();
//		players.add(new Player("Charlie Brown", "pitcher"));
//		players.add(new Player("Snoopy", "shortstop"));
//
//		team = new Team("California", "Peanuts", players);
//	}

	@Autowired
	TeamDao teamDao;

	@RequestMapping("/teams/{name}")
	public Team hiTherer(@PathVariable String name) {

		return teamDao.findByName(name);
	}
}
