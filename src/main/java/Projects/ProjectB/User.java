package Projects.ProjectB;


import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    //@OneToMany(cascade = CascadeType.ALL)
    @ElementCollection
    private List<Long> idsOfPollsVotedOn;

    //@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @ElementCollection
    private List<Long> idsOfPollsCreated;

    public User() {

    }

    public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.idsOfPollsVotedOn = new ArrayList<>();
        this.idsOfPollsCreated = new ArrayList<>();
    }

    public void createdANewPoll(Poll poll) {
        this.idsOfPollsCreated.add(poll.getId());
    }

    public void votedOnANewPoll(Poll poll) {
        this.idsOfPollsVotedOn.add(poll.getId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        // Fine tune this to take roughly 1 second
        // when deploying on cloud server.
        final int SALT_LENGTH = 16;
        final int HASH_LENGTH = 32;
        final int ITERATIONS = 2;
        final int ONE_MEGABYTE_IN_KIBIBYTES = 1024;
        final int MEMORY_REQUIRED = 64 * ONE_MEGABYTE_IN_KIBIBYTES;
        final int PARALLELISM = 1;
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(SALT_LENGTH,
                HASH_LENGTH,
                PARALLELISM,
                MEMORY_REQUIRED,
                ITERATIONS);
        this.password = passwordEncoder.encode(password);
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

    public List<Long> getPollsVotedOn() {
        return idsOfPollsVotedOn;
    }

    public void setPollsVotedOn(List<Long> idsOfPollsVotedOn) {
        this.idsOfPollsVotedOn = idsOfPollsVotedOn;
    }

    public List<Long> getPollsCreated() {
        return idsOfPollsCreated;
    }

    public void setPollsCreated(List<Long> idsOfPollsCreated) {
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
                id, userName, password, firstName, lastName
        );
    }
}
