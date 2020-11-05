package Projects.ProjectB;


import Projects.ProjectB.security.PasswordRandomizer;

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

    private String passwordAsHash;
    private String firstName;
    private String lastName;

    @ElementCollection
    private List<Long> idsOfPollsVotedOn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Poll> pollsVotedOn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Poll> pollsCreated;

    @ElementCollection
    private List<Long> idsOfPollsCreated;

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
        setPasswordAsHash(password);
        this.firstName = firstName;
        this.lastName = lastName;
//        this.idsOfPollsVotedOn = new ArrayList<>();
//        this.idsOfPollsCreated = new ArrayList<>();
    }

    public boolean verifyPassword(String password) {
        return PasswordRandomizer.passwordsMatch(password, this.passwordAsHash);
    }

    public void createdANewPoll(Poll poll) {
        this.idsOfPollsCreated.add(poll.getId());
    }

    public void votedOnANewPoll(Poll poll) {
        this.idsOfPollsVotedOn.add(poll.getId());
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordAsHash() {
        return passwordAsHash;
    }

    public void setPasswordAsHash(String password) {
        this.passwordAsHash = PasswordRandomizer.encodePassword(password);
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


    public void setPollsVotedOn(List<Long> idsOfPollsVotedOn) {
        this.idsOfPollsVotedOn = idsOfPollsVotedOn;
    }


    public List<Poll> getPollsCreated() {
        return pollsCreated;
    }

    public void setPollsCreatedID(List<Long> idsOfPollsCreated) {
        this.idsOfPollsCreated = idsOfPollsCreated;
    }

    @Override
    public String toString() {
        return String.format(
                "User[Id='%d', " +
                        "userName='%s', " +
                        "password='%s', " +
                        "firstName='%s', " +
                        "lastName='%s']",
                id, userName, passwordAsHash, firstName, lastName
        );
    }
}
