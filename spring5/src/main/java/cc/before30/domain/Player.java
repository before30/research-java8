package cc.before30.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by before30 on 04/12/2016.
 */

@Getter
@Setter
@Entity
public class Player {
    @Id
    @GeneratedValue
    Long id;

    String name;
    String position;

    public Player() {
        super();
    }

    public Player(String name, String position) {
        super();
        this.name = name;
        this.position = position;
    }
}
