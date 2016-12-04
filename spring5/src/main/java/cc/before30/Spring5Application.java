package cc.before30;

import cc.before30.example.domain.Player;
import cc.before30.example.domain.Team;
import cc.before30.example.domain.dao.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Spring5Application {

	@RestController
	public static class Controller {

		@RequestMapping("/hello")
		public String hello() {
			return "world";
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Spring5Application.class, args);
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
