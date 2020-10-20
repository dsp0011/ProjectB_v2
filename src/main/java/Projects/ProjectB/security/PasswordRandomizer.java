package Projects.ProjectB.security;

import Projects.ProjectB.ProjectBApplication;
import Projects.ProjectB.User;
import Projects.ProjectB.UserRepository;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class PasswordRandomizer {

    // If user information is compromised,
    // generate new passwords for all users.
    private static final Logger log = LoggerFactory.getLogger(ProjectBApplication.class);


    public static void randomiseAllUserPasswords(UserRepository userRepository) {
        log.info("Randomizing all user passwords");
        for (User user : userRepository.findAll()) {
            user.setPassword(generateNewPassword());
            userRepository.save(user);
        }
    }

    public static String generateNewPassword() {
        log.info("Generating a new password");
        List<CharacterRule> rules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1)
        );
        PasswordGenerator generator = new PasswordGenerator();
        return generator.generatePassword(64, rules);
    }
}
