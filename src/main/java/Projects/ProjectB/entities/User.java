package Projects.ProjectB.entities;


import Projects.ProjectB.security.PasswordRandomizer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        setPasswordAsHash(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean verifyPassword(String password) {
        return PasswordRandomizer.passwordsMatch(password, this.passwordAsHash);
    }

    public void setPasswordAsHash(String password) {
        this.passwordAsHash = PasswordRandomizer.encodePassword(password);
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
