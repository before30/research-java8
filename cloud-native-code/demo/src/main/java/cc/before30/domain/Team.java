package cc.before30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Created by before30 on 24/11/2016.
 */
@Getter
@Setter
@XmlRootElement
@Entity
public class Team {

    @Id
    @GeneratedValue
    Long id;

    String name;
    String location;
    String mascotte;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="teamId")
    Set<Player> players;

    public Team() {
        super();
    }

    public Team(String location, String name, Set<Player> players) {
        this();
        this.location = location;
        this.name = name;
        this.players = players;
    }
}
