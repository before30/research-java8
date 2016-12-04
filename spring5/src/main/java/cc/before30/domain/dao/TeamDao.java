package cc.before30.domain.dao;

import cc.before30.domain.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by before30 on 04/12/2016.
 */
@RestResource(path="teams", rel="teams")
public interface TeamDao extends CrudRepository<Team, Long>{

    List<Team> findAll();

    Team findByName(String name);

}
