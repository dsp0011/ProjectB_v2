package Projects.ProjectB.security;

import lombok.NonNull;
import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PasswordValidation {

    private static final Logger log = LoggerFactory.getLogger(PasswordValidation.class);
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 64;
    private static PasswordValidator validator;

    static {
        try {
            validator = createPasswordValidator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a password validator.
     *
     * @return The password validator.
     */
    private static PasswordValidator createPasswordValidator() throws IOException {
        log.info("Creating password validator");
        String filePath = "src/main/resources/Top_10_000_CommonlyUsedPasswords.txt";
        DictionaryRule dictionaryRule = new DictionaryRule(
                new WordListDictionary(WordLists.createFromReader(
                        new FileReader[]{new FileReader(filePath)},
                        false, new ArraysSort())));

        return new PasswordValidator(Arrays.asList(
                // A valid password must fulfill the following rules:

                // Be between MIN_PASSWORD_LENGTH and MAX_PASSWORD_LENGTH symbols long.
                new LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),

                // Contain at least one uppercase letter
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // Contain at least one lowercase letter
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // Contain at least one digit
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // Contain no whitespace
                new WhitespaceRule(),

                // Not contain a sequence of 5 or more letters or numbers in rising order
                // i.e. "rstuv" or "45678".
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),

                // Not contain a sequence of symbols in a line from the keyboard,
                // like "qwert", "asdfg" or "zxcvb".
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),

                // Not be the same as the username.
                new UsernameRule(),

                // Not be one of the 10.000 most commonly used passwords
                dictionaryRule
        ));
    }

    /**
     * Checks if the given password is valid according to the defined rules.
     *
     * @param password The password the user wants to register.
     * @param username The username of the user trying to register the password.
     * @return The result of the password validation.
     */
    public static RuleResult validatePassword(@NonNull String password,
                                              @NonNull String username) {
        log.info("Validating password");
        return validator.validate(new PasswordData(username, password));
    }

    /**
     * Get the rule violations that resulted in the password not being deemed valid.
     *
     * @param result The result of a password validation check.
     * @return The broken rules.
     */
    public static String getRuleViolations(@NonNull RuleResult result) {
        log.info("Getting rule violations for password validation result");
        List<String> messages = validator.getMessages(result);
        StringBuilder builder = new StringBuilder();
        for (String message : messages) {
            builder.append(message).append("\n");
        }
        // Remove the last new line
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    // Can possibly be used for password strength feedback
    // to users during registration and when changing password.
    /**
     * Estimate a passwords entropy, based on the validation rules.
     *
     * @param password The password to test.
     * @return An estimate of the given passwords entropy (password strength)
     */
    public static double getEntropyEstimate(String password) {
        log.info("Estimating password entropy");
        return validator.estimateEntropy(new PasswordData(password));
    }
}
