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
    private String question;
    private String alternative1;
    private String alternative2;
    private ZonedDateTime timeLimit; // The target time for when the poll should close.
    private Boolean isPublic;
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    private Vote vote = new Vote();

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<IotDevice> iotDevices;

    public Poll() {

    }

    public Poll(String question, String alternative1, String alternative2, ZonedDateTime timeLimit, Boolean isPublic, Boolean isActive, User creator) {
        this.question = question;
        this.alternative1 = alternative1;
        this.alternative2 = alternative2;
        this.timeLimit = timeLimit;
        this.isPublic = isPublic;
        this.isActive = isActive;
        this.creator = creator;
        this.vote = new Vote();
        this.iotDevices = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAlternative1() {
        return alternative1;
    }

    public void setAlternative1(String alternative1) {
        this.alternative1 = alternative1;
    }

    public String getAlternative2() {
        return alternative2;
    }

    public void setAlternative2(String alternative2) {
        this.alternative2 = alternative2;
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

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public String toString() {
        return String.format(
                "Poll[Id='%d', Question 1='%s', Alternative 1='%s', Alternative 2='%s', Public='%s', Active='%s', Time Limit='%s', Creator='%s', Vote='%s']",
                id, question, alternative1, alternative2, isPublic, isActive, timeLimit, creator, vote
        );
    }
}
