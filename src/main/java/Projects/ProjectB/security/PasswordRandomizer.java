package Projects.ProjectB.security;

import Projects.ProjectB.User;
import Projects.ProjectB.UserRepository;
import org.passay.CharacterData;
import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordRandomizer {

    private static final Logger log = LoggerFactory.getLogger(PasswordRandomizer.class);
    private static final PasswordGenerator generator = new PasswordGenerator();
    private static final CharacterRule PASSWORD_GENERATION_RULE = new CharacterRule(new CharacterData() {
        @Override
        public String getErrorCode() {
            return "INSUFFICIENT_REQUIRED_CHARACTERS";
        }

        @Override
        public String getCharacters() {
            return EnglishCharacterData.Alphabetical.getCharacters() +
                    EnglishCharacterData.Digit.getCharacters() +
                    "?!_+-=";
        }
    }, 1);

    public static void randomiseOneUsersPassword(UserRepository userRepository, User user) {
        log.info("Replacing user password with a generated one");
        user.setPasswordAsHash(generateNewPassword());
        userRepository.save(user);
    }

    // If user information is compromised we
    // can generate new passwords for all users.
    public static void randomiseAllUserPasswords(UserRepository userRepository) {
        log.info("Randomizing all user passwords");
        for (User user : userRepository.findAll()) {
            user.setPasswordAsHash(generateNewPassword());
            userRepository.save(user);
        }
    }

    public static String generateNewPassword() {
        log.info("Generating a new password");
        return generator.generatePassword(PasswordValidation.MAX_PASSWORD_LENGTH, PASSWORD_GENERATION_RULE);
    }
}
