package Projects.ProjectB;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAlternative1() {
        return alternative1;
    }

    public void setAlternative1(int alternative1) {
        this.alternative1 = alternative1;
    }

    public int getAlternative2() {
        return alternative2;
    }

    public void setAlternative2(int alternative2) {
        this.alternative2 = alternative2;
    }

    @Override
    public String toString() {
        return String.format(
                "Vote[Id='%d', Red='%d', Green='%d']",
                id, alternative1, alternative2
        );
    }
}
