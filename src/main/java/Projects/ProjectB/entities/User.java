package Projects.ProjectB.entities;


import Projects.ProjectB.security.PasswordRandomizer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pollUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String userName;

    @JsonIgnore // Enable to prevent password from being used in output.
    @Setter(AccessLevel.NONE)private String passwordAsHash;
    private String firstName;
    private String lastName;

   // @ElementCollection
   // private List<Long> idsOfPollsVotedOn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Poll> pollsVotedOn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Poll> pollsCreated;

  //  @ElementCollection
  //  private List<Long> idsOfPollsCreated;


    public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        setPasswordAsHash(password);
        this.firstName = firstName;
        this.lastName = lastName;
   //     this.idsOfPollsVotedOn = new ArrayList<>();
   //     this.idsOfPollsCreated = new ArrayList<>();
    }

    public boolean verifyPassword(String password) {
        return PasswordRandomizer.passwordsMatch(password, this.passwordAsHash);
    }

   // public void createdANewPoll(Poll poll) {
   //     this.idsOfPollsCreated.add(poll.getId());
  //  }

  //  public void votedOnANewPoll(Poll poll) {
   //     this.idsOfPollsVotedOn.add(poll.getId());
   // }

    public void setPasswordAsHash(String password) {
        this.passwordAsHash = PasswordRandomizer.encodePassword(password);
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

    @Override
    public String toString() {
        return String.format(
                "User[Id='%d', " +
                        "userName='%s', " +
                        "firstName='%s', " +
                        "lastName='%s']",
                id, userName, firstName, lastName
        );
    }
}
