package cc.before30.domain.dao;

import cc.before30.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by before30 on 24/11/2016.
 */
public interface TeamDao extends CrudRepository<Team, Long> {

    List<Team> findAll();

    Team findByName(String name);

}
