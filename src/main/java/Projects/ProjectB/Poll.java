package Projects.ProjectB;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private ZonedDateTime timeLimit; // The target time for when the poll should close.
    private Boolean isPublic;
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    private Vote vote;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<IotDevice> iotDevices;

    public Poll() {

    }

    public Poll(ZonedDateTime timeLimit, Boolean isPublic, Boolean isActive, User creator, Vote vote) {
        this.timeLimit = timeLimit;
        this.isPublic = isPublic;
        this.isActive = isActive;
        this.vote = vote;
        this.creator = creator;
        this.iotDevices = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(ZonedDateTime timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<IotDevice> getIotDevices() {
        return iotDevices;
    }

    public void setIotDevices(List<IotDevice> iotDevices) {
        this.iotDevices = iotDevices;
    }
}
