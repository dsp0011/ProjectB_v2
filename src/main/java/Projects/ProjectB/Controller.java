package Projects.ProjectB;

import Projects.ProjectB.security.PasswordRandomizer;
import Projects.ProjectB.security.PasswordValidation;
import org.passay.RuleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	private final AdminTools adminTools = new AdminTools();
	final UserRepository userRepository;
	final PollRepository pollRepository;
	final IoTDeviceRepository ioTDeviceRepository;

	@Autowired
	public Controller(UserRepository userRepository,
					  PollRepository pollRepository,
					  IoTDeviceRepository ioTDeviceRepository) {
		this.userRepository = userRepository;
		this.pollRepository = pollRepository;
		this.ioTDeviceRepository = ioTDeviceRepository;
	}

/*
			USER REQUESTS
 */
    @PostMapping("/users")
    public String createUser(@RequestBody Map<String, String> json) {
		log.info("Attempting to create a new user");
		// Concatenating request parameters with an empty
		// string to prevent null values.
    	String userName = "" + json.get("userName");
    	String password = "" + json.get("password");
    	String repeatPassword = "" + json.get("repeatPassword");
    	String firstName = "" + json.get("firstName");
    	String lastName = "" + json.get("lastName");
    	// TODO: Check if firstname and lastname is empty?
		Instant start = Instant.now();
    	User user = new User(userName, password, firstName, lastName);
		Instant end = Instant.now();
		log.info("Hashing the users password took roughly " +
				ChronoUnit.MILLIS.between(start, end) + " ms");
    	return evaluateUserCredentials(user, password, repeatPassword);
    }

    /**
     * Evaluate the given user credentials.
     *
     * @param user The user to check.
     * @param password The password to check.
     * @param repeatPassword The repeated password for confirmation.
     * @return The result of the evaluation.
     */
    private String evaluateUserCredentials(User user, String password, String repeatPassword) {
        log.info("Evaluating user credentials");
        String userName = user.getUserName();
        String result = evaluateUsername(userName);
        if (!result.isEmpty()) {
            return result;
        }
        result = evaluatePassword(password, userName);
        if (!result.isEmpty()) {
            return result;
        }
        if (password.equals(repeatPassword)) {
            userRepository.save(user);
            log.info("Saved user with valid password");
            return "User saved";
        } else {
            log.info("Password and repeated password did not match");
            return "User was not saved\n" +
                    "Password and repeated password did not match";
        }
    }

    /**
     * Check if the password is valid.
     *
     * @param password The password to check.
     * @param userName The username of the user.
     * @return The result of the evaluation.
     */
    private String evaluatePassword(String password, String userName) {
        RuleResult result = PasswordValidation.validatePassword(password, userName);
        if (!result.isValid()) {
            log.info("Password was not valid");
            return "User was not saved\n" +
                    PasswordValidation.getRuleViolations(result);
        }
        return ""; // Nothing wrong with the password.
    }

	/**
	 * Check that the username is not too long or too short,
	 * and is not already being used by someone else.
	 *
	 * @param userName Username to be checked.
	 * @return The result of the evaluation.
	 */
	private String evaluateUsername(String userName) {
		final int MIN_USERNAME_LENGTH = 3;
		final int MAX_USERNAME_LENGTH = 100;
		if (userName.length() < MIN_USERNAME_LENGTH) {
			log.info("Username was too short");
			return "User was not saved\n" +
					"Username is too short";
		}
		if (userName.length() > MAX_USERNAME_LENGTH) {
			log.info("Username was too long");
			return "User was not saved\n" +
					"Username is too long";
		}
		if (userRepository.findByUserName(userName) != null) {
			log.info("Username is already taken");
			return "User was not saved\n" +
					"Username is already taken";
		}
		return ""; // Nothing wrong with the username.
	}

	@GetMapping("/users")
	public @ResponseBody Iterable<User> getAllUsers() {
    	log.info("Getting all users");
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@GetMapping("/users/{userName}")
	public @ResponseBody User getUser(@PathVariable String userName) {
    	log.info("Getting specific user");
		// This returns a JSON or XML with the users
		return userRepository.findByUserName(userName);
	}

	@PutMapping("/users/{userName}")
	public @ResponseBody User updateUser(@PathVariable String userName,
										 @RequestBody Map<String, String> json) {
    	log.info("Attempting to alter existing user");
		User user = userRepository.findByUserName(userName);
    	if (user == null) {
    		log.info("User did not exist in the database");
    		return null;
		}
		String newUserName = "" + json.get("newUserName");
		String newPassword = "" + json.get("newPassword");
		String repeatedNewPassword = "" + json.get("repeatedNewPassword");
		String newFirstName = "" + json.get("newFirstName");
		String newLastName = "" + json.get("newLastName");
		updateFirstName(user, newFirstName);
		updateLastName(user, newLastName);
		updateUsername(user, newUserName);
		updatePassword(user, newPassword, repeatedNewPassword);
		return user;
	}

	private void updateFirstName(User user, String newFirstName) {
		if (!newFirstName.isEmpty() && !newFirstName.equals("null")) {
			user.setFirstName(newFirstName);
			userRepository.save(user);
			log.info("Updated user's firstName");
		}
	}

	private void updateLastName(User user, String newLastName) {
		if (!newLastName.isEmpty() && !newLastName.equals("null")) {
			user.setLastName(newLastName);
			userRepository.save(user);
			log.info("Updated user's lastName");
		}
	}

	private void updateUsername(User user, String newUserName) {
		if (!newUserName.isEmpty() && !newUserName.equals("null")) {
			if (evaluateUsername(newUserName).isEmpty()) {
				if (!PasswordRandomizer.passwordsMatch(newUserName, user.getPasswordAsHash())) {
					// New username cant be equal to the existing password.
					user.setUserName(newUserName);
					userRepository.save(user);
					log.info("Updated user's username");
				} else {
					log.info("New username was not valid");
				}
			} else {
				log.info("New username was not valid");
			}
		}
	}

	private void updatePassword(User user, String newPassword, String repeatedNewPassword) {
		if (!newPassword.isEmpty()  && !newPassword.equals("null")) {
			if (evaluatePassword(newPassword, user.getUserName()).isEmpty()) {
				if (newPassword.equals(repeatedNewPassword)) {
					user.setPasswordAsHash(newPassword);
					userRepository.save(user);
					log.info("Updated user's password");
				} else {
					log.info("New password and repeated password did not match");
				}
			} else {
				log.info("New password was not valid");
			}
		}
	}

	@DeleteMapping("/users/{userName}")
	public String deleteUser(@PathVariable String userName) {
    	log.info("Attempting to delete a user");
		userRepository.delete(userRepository.findByUserName(userName));
		return "User deleted";
	}

/*
			POLL REQUESTS
 */

    @PostMapping("/polls")
	public String createPoll(@RequestBody Map<String, String> json) {
    	log.info("Attempting to create a new poll");
    	String question = "" + json.get("question");
    	String alternative1 = "" + json.get("alternative1");
    	String alternative2 = "" + json.get("alternative2");
    	String timeLimit = "" + json.get("timeLimit");
    	boolean isPublic = Boolean.parseBoolean("" + json.get("public"));
    	String creatorUserName = "" + json.get("creator");
		User creator = userRepository.findByUserName(creatorUserName);
		if (creator == null) {
			log.info("Creator did not exist in the database");
			return "Poll was not saved\n" +
					"Creator does not exist";
		} else {
			Poll poll = new Poll(question, alternative1, alternative2,
					timeLimit, isPublic, false, true, creator);
			pollRepository.save(poll);
			creator.createdANewPoll(poll);
			userRepository.save(creator);
			log.info("Poll was created successfully");
			return "Poll saved";
		}
	}

	@GetMapping("/polls")
	public @ResponseBody Iterable<Poll> getAllPolls() {
    	log.info("Getting all polls");
		// This returns a JSON or XML with the users
		return pollRepository.findAll();
	}

	@GetMapping("/polls/{id}")
	public @ResponseBody Poll getPoll(@PathVariable long id) {
    	log.info("Attempting to get existing poll");
		// This returns a JSON or XML with the users
		return pollRepository.findById(id);
	}

	@PutMapping("/polls/{id}")
	public @ResponseBody
	Poll updatePoll(@PathVariable long id, @RequestBody Map<String, String> json) {
		log.info("Attempting to alter existing poll");
		Poll oldPoll = pollRepository.findById(id);
		if (oldPoll == null) {
			log.info("Poll did not exist in the database");
			return null;
		} else if (!oldPoll.getCanEdit()) {
			log.info("Poll was already published and could not be altered");
			return oldPoll;
		} else {
			String question = "" + json.get("question");
			String alternative1 = "" + json.get("alternative1");
			String alternative2 = "" + json.get("alternative2");
			String timeLimit = "" + json.get("timeLimit");
			boolean isPublic = Boolean.parseBoolean("" + json.get("public"));

			oldPoll.setQuestion(question);
			oldPoll.setAlternative1(alternative1);
			oldPoll.setAlternative2(alternative2);
			oldPoll.setTimeLimit(timeLimit);
			oldPoll.setPublic(isPublic);
			log.info("Successfully updated the poll");
			return pollRepository.save(oldPoll);
		}
	}

	@DeleteMapping("/polls/{id}")
	public String deletePoll(@PathVariable long id) {
    	log.info("Attempting to delete a poll");
		pollRepository.delete(pollRepository.findById(id));
		return "Poll deleted";
	}

	/*
			IOT DEVICE REQUESTS
 	*/

	@PostMapping("/devices")
	public String createDevice(@RequestBody IotDevice iotDevice) {
		log.info("Creating a new IoT device");
		ioTDeviceRepository.save(iotDevice);
		return "Device saved";
	}

	@GetMapping("/devices")
	public @ResponseBody Iterable<IotDevice> getAllDevices() {
		log.info("Getting all IoT devices");
		// This returns a JSON or XML with the devices
		return ioTDeviceRepository.findAll();
	}

	@GetMapping("/devices/{id}")
	public @ResponseBody IotDevice getDevice(@PathVariable long id) {
		log.info("Attempting to get existing IoT device");
		// This returns a JSON or XML with the devices
		return ioTDeviceRepository.findById(id);
	}

	@PutMapping("/devices/{id}")
	public @ResponseBody IotDevice updatePoll(@PathVariable long id, @RequestBody IotDevice iotDevice) {
		log.info("Attempting to attach an IoT device to an existing poll");
		IotDevice oldDevice = ioTDeviceRepository.findById(id);
		oldDevice.setPoll(iotDevice.getPoll());

		return ioTDeviceRepository.save(oldDevice);
	}

	@DeleteMapping("/devices/{id}")
	public String deleteDevice(@PathVariable long id) {
		log.info("Attempting to delete an existing IoT device");
		ioTDeviceRepository.delete(ioTDeviceRepository.findById(id));
		return "Device deleted";
	}

	/*
			VOTE REQUESTS
	 */

	@PutMapping("/votes/{id}")
	public @ResponseBody Poll updateVote(@PathVariable long id, @RequestBody Vote vote) {
		log.info("Attempting to alter an existing polls vote");
		Poll poll = pollRepository.findById(id);
		Vote newVote = poll.getVote();
		newVote.setAlternative1(newVote.getAlternative1() + vote.getAlternative1());
		newVote.setAlternative2(newVote.getAlternative2() + vote.getAlternative2());
		poll.setVote(newVote);

		return pollRepository.save(poll);
	}


	private class AdminTools {
		public void resetUserPassword(User user) {
			PasswordRandomizer.randomiseOneUsersPassword(userRepository, user);
		}

		public void resetAllUserPasswords() {
			PasswordRandomizer.randomiseAllUserPasswords(userRepository);
		}
	}

}