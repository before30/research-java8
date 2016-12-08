package cc.before30.example.domain.dao;

import cc.before30.example.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by before30 on 04/12/2016.
 */
@RestResource(path="players", rel="players")
public interface PlayersDao extends CrudRepository<Player, Long> {
}