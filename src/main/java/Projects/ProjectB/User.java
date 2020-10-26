package Projects.ProjectB;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pollUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Poll> pollsVotedOn;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Poll> pollsCreated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public User() {

    }

    public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pollsVotedOn = new ArrayList<>();
        this.pollsCreated = new ArrayList<>();
    }

    public void createPoll(Poll poll) {
        this.pollsCreated.add(poll);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Poll> getPollsVotedOn() {
        return pollsVotedOn;
    }

    public void addPollCreated(Poll pollCreated) {
        if (this.pollsCreated == null) {
            this.pollsCreated = new ArrayList<>();
            this.pollsCreated.add(pollCreated);
        } else {
            this.pollsCreated.add(pollCreated);
        }
    }

    public void addPollVotedOn(Poll pollVotedOn) {
        if (this.pollsVotedOn == null) {
            this.pollsVotedOn = new ArrayList<>();
            this.pollsVotedOn.add(pollVotedOn);
        } else {
            this.pollsVotedOn.add(pollVotedOn);
        }
    }

    public void setPollsVotedOn(List<Poll> pollsVotedOn) {
        if (this.pollsVotedOn == null) {
            this.pollsVotedOn = pollsVotedOn;
        }
        else {
            this.pollsVotedOn.add(pollsVotedOn.get(0)); // TODO fix multiple votes same user error handling
        }
    }

    public List<Poll> getPollsCreated() {
        return pollsCreated;
    }

    public void setPollsCreated(List<Poll> pollsCreated) {
        if (this.pollsCreated == null) {
            this.pollsCreated = pollsCreated;
        }
        else {
            this.pollsCreated.add(pollsCreated.get(0)); // TODO fix multiple votes same user error handling
        }
    }

    @Override
    public String toString() {
        return String.format(
                "User[userName='%s', password='%s', firstName='%s', lastName='%s']",
                userName, password, firstName, lastName
        );
    }
}
