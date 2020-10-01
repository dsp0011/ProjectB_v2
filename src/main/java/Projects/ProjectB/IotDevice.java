package Projects.ProjectB;

import javax.persistence.*;

@Entity
public class IotDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Poll poll;

    public IotDevice() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public String toString() {
        return String.format(
                "IoT Device[Id='%d']", id
        );
    }
}
