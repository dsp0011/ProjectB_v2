package Projects.ProjectB.security;

import Projects.ProjectB.User;
import Projects.ProjectB.UserRepository;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    // Fine tune this to take roughly 1 second
    // when deploying on cloud server.
    static final int SALT_LENGTH = 16;
    static final int HASH_LENGTH = 32;
    static final int ITERATIONS = 2;
    static final int ONE_MEGABYTE_IN_KIBIBYTES = 1024;
    static final int MEMORY_REQUIRED = 64 * ONE_MEGABYTE_IN_KIBIBYTES;
    static final int PARALLELISM = 1;

    public static String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(SALT_LENGTH,
                HASH_LENGTH,
                PARALLELISM,
                MEMORY_REQUIRED,
                ITERATIONS);
        return passwordEncoder.encode(password);
    }

    public static boolean passwordsMatch(String password, String hashedPassword) {
        PasswordEncoder encoder = new Argon2PasswordEncoder();
        return encoder.matches(password, hashedPassword);
    }
}
