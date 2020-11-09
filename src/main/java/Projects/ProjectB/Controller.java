package Projects.ProjectB;

import Projects.ProjectB.rabbitmq.Publisher;
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
	final Publisher publisher;

	@Autowired
	public Controller(UserRepository userRepository,
					  PollRepository pollRepository,
					  IoTDeviceRepository ioTDeviceRepository,
					  Publisher publisher) {
		this.userRepository = userRepository;
		this.pollRepository = pollRepository;
		this.ioTDeviceRepository = ioTDeviceRepository;
		this.publisher = publisher;
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


	@PutMapping("/users/createPoll/{userName}")
	public String updateUserPollsCreated(@PathVariable String userName, @RequestBody Map<String, String> json) {
		String question = "" + json.get("question");
		String alternative1 = "" + json.get("alternative1");
		String alternative2 = "" + json.get("alternative2");
		String timeLimit = "" + json.get("timeLimit");
		boolean isPublic = Boolean.parseBoolean("" + json.get("public"));
		String creatorUserName = "" + json.get("creator");
		User creator = userRepository.findByUserName(creatorUserName);

		Poll poll = new Poll(question, alternative1, alternative2,
				timeLimit, isPublic, false, true, null);

		creator.addPollCreated(poll);
		userRepository.save(creator);
		return "";

	}

	@PutMapping("/users/participatePoll/{userName}")
	public String updateUserPollsParticipated(@PathVariable String userName, @RequestBody Map<String, String> json) {
		int pollID = Integer.parseInt(json.get("pollID"));
		String creatorUserName = "" + json.get("creator");
		User creator = userRepository.findByUserName(creatorUserName);

		Poll poll = pollRepository.findById(pollID);
		creator.addPollVotedOn(poll);
		userRepository.save(creator);
		return "";

	}

//
//	@PutMapping("/users/createPoll/{userName}")
//	@CrossOrigin(origins = "*")
//	public @ResponseBody User updateUserPollsCreated(@PathVariable String userName, @RequestBody User user) {
//		System.out.println("user: " + user);
//		User oldUser = userRepository.findByUserName(userName);
//		oldUser.addPollCreated(user.getPollsCreated().get(0));
//		return userRepository.save(oldUser);
//	}


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
		User user = userRepository.findByUserName(userName);
		if (user != null) {
			removeUsersConnectionToCreatedPolls(user);
			long userId = user.getId();
			userRepository.delete(user);
			log.info("Successfully deleted the user with ID: " + userId);
			return "User deleted";
		} else {
			log.info("User did not exist");
			return "User was not deleted\n" +
					"User did not exist in the database";
		}
	}

    private void removeUsersConnectionToCreatedPolls(User user) {
	    // Removes foreign key constraints.
        for (Poll poll: user.getPollsCreated()) {
            poll.setCreator(null);
        }
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

		Poll poll = pollRepository.findById(id);

		// This returns a JSON or XML with the users
		return pollRepository.findById(id);
	}

	@PutMapping("/polls/{id}")
	@CrossOrigin(origins = "*")
	public @ResponseBody
	Poll updatePoll(@PathVariable long id,
					@RequestBody Map<String, String> json) {
		log.info("Attempting to alter existing poll");
		Poll poll = pollRepository.findById(id);
		if (poll == null) {
			log.info("Poll did not exist in the database");
			return null;
		} else if (!poll.getCanEdit()) {
			boolean closePoll = Boolean.parseBoolean("" + json.get("closePoll"));
			if (closePoll) {
				closeThePoll(poll);
			} else {
				log.info("Poll was already published and could not be altered");
			}
			return poll;
		} else {
			String newQuestion = "" + json.get("newQuestion");
			String newAlternative1 = "" + json.get("newAlternative1");
			String newAlternative2 = "" + json.get("newAlternative2");
			String newTimeLimit = "" + json.get("newTimeLimit");
			String newIsPublic = "" + json.get("stillPublic");
			boolean publish = Boolean.parseBoolean("" + json.get("publishPoll"));
			updateQuestion(poll, newQuestion);
			updateAlternative1(poll, newAlternative1);
			updateAlternative2(poll, newAlternative2);
			updateTimeLimit(poll, newTimeLimit);
			updateIsPublic(poll, newIsPublic);
			if (publish) {
				publishThePoll(poll);
			}
			log.info("Successfully updated the poll");
			return poll;
		}
	}

	private void closeThePoll(Poll poll) {
		if (poll.getActive() && !poll.getCanEdit()) {
			// Only close if poll is published and active.
			poll.closePoll();
			pollRepository.save(poll);

			// Publish message
			publisher.sendMessage(poll, "poll.close");

			log.info("The Poll was closed");
		} else {
			log.info("The poll was already closed");
		}
	}

	private void updateQuestion(Poll oldPoll, String newQuestion) {
		if (!newQuestion.isEmpty() && !newQuestion.equals("null")) {
			oldPoll.setQuestion(newQuestion);
			pollRepository.save(oldPoll);
			log.info("Updated the poll's question");
		}
	}

	private void updateAlternative1(Poll oldPoll, String newAlternative1) {
		if (!newAlternative1.isEmpty() && !newAlternative1.equals("null")) {
			oldPoll.setAlternative1(newAlternative1);
			pollRepository.save(oldPoll);
			log.info("Updated the poll's alternative1");
		}
	}

	private void updateAlternative2(Poll oldPoll, String newAlternative2) {
		if (!newAlternative2.isEmpty() && !newAlternative2.equals("null")) {
			oldPoll.setAlternative2(newAlternative2);
			pollRepository.save(oldPoll);
			log.info("Updated the poll's alternative2");
		}
	}

	private void updateTimeLimit(Poll poll, String newTimeLimit) {
		if (!newTimeLimit.isEmpty() && !newTimeLimit.equals("null")) {
			poll.setTimeLimit(newTimeLimit);
			pollRepository.save(poll);
			log.info("Updated the poll's time limit");
		}
	}

	private void updateIsPublic(Poll poll, String newIsPublic) {
		if (!newIsPublic.isEmpty() && !newIsPublic.equals("null")) {
			poll.setPublic(Boolean.parseBoolean(newIsPublic));
			pollRepository.save(poll);
			log.info("Updated the poll's public status");
		}
	}

	private void publishThePoll(Poll poll) {
		if (poll.getCanEdit() && !poll.getActive()) {
			// Only publish the poll if it has not yet been published.
			poll.publishPoll();
			pollRepository.save(poll);

			// Publish message
			publisher.sendMessage(poll, "poll.open");

			log.info("The poll was published");
		} else {
			log.info("The poll has already been published");
		}
	}

	@DeleteMapping("/polls/{id}")
	public String deletePoll(@PathVariable long id) {
		log.info("Attempting to delete a poll");
		Poll poll = pollRepository.findById(id);
		if (poll != null) {
			long pollId = poll.getId();
			pollRepository.delete(poll);
			log.info("Successfully deleted the poll with ID: " + pollId);
			return "Poll deleted";
		} else {
			log.info("Poll did not exist");
			return "Poll was not deleted\n" +
					"Poll did not exist in the database";
		}
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
		IotDevice iotDevice = ioTDeviceRepository.findById(id);
		if (iotDevice != null) {
			long iotDeviceId = iotDevice.getId();
			ioTDeviceRepository.delete(iotDevice);
			log.info("Successfully deleted the IoT device with ID: " + iotDeviceId);
			return "Device deleted";
		} else {
			log.info("IoT device did not exist");
			return "Device was not deleted\n" +
					"Device did not exist in the database";
		}
	}

	/*
			VOTE REQUESTS
	 */

	@PutMapping("/votes/{id}")
	@CrossOrigin(origins = "*")
	public @ResponseBody Poll updateVote(@PathVariable long id, @RequestBody Vote vote) {
		log.info("Attempting to alter an existing polls vote");
		Poll poll = pollRepository.findById(id);
		if (poll == null) {
			log.info("Poll did not exist");
			return null;
		}
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