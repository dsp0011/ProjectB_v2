package Projects.ProjectB;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pollUser")
public class User {
    @Id
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Poll> pollsVotedOn;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Poll> pollsCreated;

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

    public void setPollsVotedOn(List<Poll> pollsVotedOn) {
        this.pollsVotedOn = pollsVotedOn;
    }

    public List<Poll> getPollsCreated() {
        return pollsCreated;
    }

    public void setPollsCreated(List<Poll> pollsCreated) {
        this.pollsCreated = pollsCreated;
    }

	@Override
	public String toString() {
		return String.format(
				"User[userName=%s, password='%s', firstName='%s', lastName='%s']",
				userName, password, firstName, lastName);
	}
}
