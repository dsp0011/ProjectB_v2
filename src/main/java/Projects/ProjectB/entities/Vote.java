package Projects.ProjectB.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int alternative1;
    private int alternative2;

    public Vote() {
        this.alternative1 = 0;
        this.alternative2 = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Vote[Id='%d', Red='%d', Green='%d']",
                id, alternative1, alternative2
        );
    }
}
