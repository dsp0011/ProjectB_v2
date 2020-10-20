package Projects.ProjectB.security;

import org.passay.*;

import java.util.Arrays;
import java.util.List;

public class PasswordValidation {

    private static final PasswordValidator passwordValidator = createPasswordValidator();

    private static PasswordValidator createPasswordValidator() {
        return new PasswordValidator(Arrays.asList(
                // A valid password must fulfill the following rules:

                // Be between 8 and 25 symbols long.
                new LengthRule(8, 25),

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
                new UsernameRule()
        ));
    }




    public static boolean validatePassword(String suggestedPassword,
                                           String repeatedPassword,
                                           String suggestedUsername) {
        if (suggestedPassword == null || repeatedPassword == null || suggestedUsername == null) {
            System.out.println("Input can't be null");
            return false;
        }

        RuleResult result = passwordValidator.validate(new PasswordData(suggestedUsername, suggestedPassword));
        if (!result.isValid()) {
            System.out.println("Password is not valid");
            printRuleViolations(result);
            return false;
        }

        if (suggestedPassword.equals(repeatedPassword)) {
            System.out.println("suggested and repeat are equal");
            return true;
        } else {
            System.out.println("Password and repeated password did not match");
            return false;
        }
    }

    /**
     * Print the rule violations that resulted in the password not being deemed valid.
     *
     * @param result The result of a password validation check.
     */
    private static void printRuleViolations(RuleResult result) {
        List<String> messages = passwordValidator.getMessages(result);
        for (String message : messages) {
            System.out.println("message = " + message);
        }
    }
}
