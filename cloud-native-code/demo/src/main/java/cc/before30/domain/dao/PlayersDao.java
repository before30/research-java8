package cc.before30.domain.dao;

import cc.before30.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by before30 on 24/11/2016.
 */
@RestResource(path="players", rel="players")
public interface PlayersDao extends CrudRepository<Player, Long> {
}
