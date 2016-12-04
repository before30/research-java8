package cc.before30.controller;

import cc.before30.domain.Team;
import cc.before30.domain.dao.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by before30 on 04/12/2016.
 */
@RestController
public class ApiController {
    @Autowired
    TeamDao teamDao;

    @RequestMapping("/api/teams/{name}")
    public Team teams(@PathVariable String name) {
        return teamDao.findByName(name);
    }
}
