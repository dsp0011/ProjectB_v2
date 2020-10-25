package Projects.ProjectB;

import Projects.ProjectB.security.PasswordRandomizer;
import Projects.ProjectB.security.PasswordValidation;
import Projects.ProjectB.time.ITimeDuration;
import org.passay.RuleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    	User user = new User(userName, password, firstName, lastName);


		System.out.println("password = " + password);
		System.out.println("repeatPassword = " + repeatPassword);
		System.out.println("userName = " + userName);

		//adminTools.resetAllUserPasswords();

        return evaluateUserCredentials(user, password, repeatPassword);
    }

    private String evaluateUserCredentials(User user, String password, String repeatPassword) {
    	log.info("Evaluating user credentials");
        String userName = user.getUserName();
        if (userRepository.findByUserName(userName) != null) {
            log.info("Username is already taken");
            return "User was not saved\n" +
                    "Username is already taken";
        }

        RuleResult result = PasswordValidation.validatePassword(password, userName);
        if (!result.isValid()) {
            log.info("Password was not valid");
            return "User was not saved\n" +
                    PasswordValidation.getRuleViolations(result);
        }

        if (password.equals(repeatPassword)) {
            // TODO: Password should be hashed with salt in User class.
            userRepository.save(user);
            log.info("Saved user with valid password");
            return "User saved";
        } else {
            log.info("Password and repeated password did not match");
            return "User was not saved\n" +
                    "Password and repeated password did not match";
        }
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
	public @ResponseBody User updateUser(@PathVariable String userName, @RequestBody User user) {
    	log.info("Attempting to alter existing user");
		User oldUser = userRepository.findByUserName(userName);
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		oldUser.setPassword(user.getPassword());
		oldUser.setPollsCreated(user.getPollsCreated());
		oldUser.setPollsVotedOn(user.getPollsVotedOn());

		return userRepository.save(oldUser);
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
    	boolean isPublic = Boolean.parseBoolean("" + json.get("isPublic"));
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
	public @ResponseBody Poll updatePoll(@PathVariable long id, @RequestBody Poll poll) {
    	log.info("Attempting to alter existing poll");
		Poll oldPoll = pollRepository.findById(id);
		boolean wasActive = oldPoll.getActive();
		boolean isActive = poll.getActive();
		oldPoll.setQuestion(poll.getQuestion());
		oldPoll.setAlternative1(poll.getAlternative1());
		oldPoll.setAlternative2(poll.getAlternative2());
		oldPoll.setPublic(poll.getPublic());
		oldPoll.setActive(poll.getActive());
		oldPoll.setCreator(poll.getCreator());
		oldPoll.setTimeLimit(poll.getTimeLimit());
		if (wasActive && !isActive) { // If poll closes, do not update closing date.
			oldPoll.setPollClosingDate(oldPoll.getPollClosingDate());
		} else {
			String newPollClosingDate = ITimeDuration
					.timeDurationFromStringOfTimeUnits(oldPoll.getTimeLimit())
					.futureZonedDateTimeFromTimeDuration()
					.toString();
			oldPoll.setPollClosingDate(newPollClosingDate);
		}
		oldPoll.setVote(poll.getVote());
		oldPoll.setIotDevices(poll.getIotDevices());

		return pollRepository.save(oldPoll);
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