package Projects.ProjectB;

import Projects.ProjectB.time.ITimeDuration;
import Projects.ProjectB.time.TimeDuration;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {

@Autowired
private UserRepository userRepository;

@Autowired
private PollRepository pollRepository;

@Autowired
private IoTDeviceRepository ioTDeviceRepository;


/*
			USER REQUESTS
 */
    @PostMapping("/users")
    public String createUser(@RequestBody User user) {
        userRepository.save(user);
        return "User saved";
    }

	@GetMapping("/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@GetMapping("/users/{userName}")
	public @ResponseBody User getUser(@PathVariable String userName) {
		// This returns a JSON or XML with the users
		return userRepository.findByUserName(userName);
	}

	@PutMapping("/users/{userName}")
	public @ResponseBody User updateUser(@PathVariable String userName, @RequestBody User user) {
		User oldUser = userRepository.findByUserName(userName);
		if (user.getFirstName() != null)
			oldUser.setFirstName(user.getFirstName());
		if (user.getLastName() != null)
			oldUser.setLastName(user.getLastName());
		if (user.getPassword() != null)
			oldUser.setPassword(user.getPassword());
		if (user.getPollsCreated() != null)
			oldUser.addPollCreated(user.getPollsCreated().get(0));
		if (user.getPollsVotedOn() != null)
		    oldUser.addPollVotedOn(user.getPollsVotedOn().get(0));
		return userRepository.save(oldUser);
	}

	@DeleteMapping("/users/{userName}")
	public String deleteUser(@PathVariable String userName) {
		userRepository.delete(userRepository.findByUserName(userName));
		return "User deleted";
	}


/*
			POLL REQUESTS
 */

    @PostMapping("/polls")
	public String createPoll(@RequestBody Poll poll) {
		pollRepository.save(poll);
		return "Poll saved";
	}

	@GetMapping("/polls")
	public @ResponseBody Iterable<Poll> getAllPolls() {
		// This returns a JSON or XML with the users
		return pollRepository.findAll();
	}

	@GetMapping("/polls/{id}")
	public @ResponseBody Poll getPoll(@PathVariable long id) {
		// This returns a JSON or XML with the users
		return pollRepository.findById(id);
	}

	@PutMapping("/polls/{id}")
	public @ResponseBody Poll updatePoll(@PathVariable long id, @RequestBody Poll poll) {
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
		pollRepository.delete(pollRepository.findById(id));
		return "Poll deleted";
	}

	/*
			IOT DEVICE REQUESTS
 	*/

	@PostMapping("/devices")
	public String createDevice(@RequestBody IotDevice iotDevice) {
		ioTDeviceRepository.save(iotDevice);
		return "Device saved";
	}

	@GetMapping("/devices")
	public @ResponseBody Iterable<IotDevice> getAllDevices() {
		// This returns a JSON or XML with the devices
		return ioTDeviceRepository.findAll();
	}

	@GetMapping("/devices/{id}")
	public @ResponseBody IotDevice getDevice(@PathVariable long id) {
		// This returns a JSON or XML with the devices
		return ioTDeviceRepository.findById(id);
	}

	@PutMapping("/devices/{id}")
	public @ResponseBody IotDevice updatePoll(@PathVariable long id, @RequestBody IotDevice iotDevice) {
		IotDevice oldDevice = ioTDeviceRepository.findById(id);
		oldDevice.setPoll(iotDevice.getPoll());

		return ioTDeviceRepository.save(oldDevice);
	}

	@DeleteMapping("/devices/{id}")
	public String deleteDevice(@PathVariable long id) {
		ioTDeviceRepository.delete(ioTDeviceRepository.findById(id));
		return "Device deleted";
	}

	/*
			VOTE REQUESTS
	 */

	@PutMapping("/votes/{id}")
	public @ResponseBody Poll updateVote(@PathVariable long id, @RequestBody Vote vote) {
		Poll poll = pollRepository.findById(id);
		Vote newVote = poll.getVote();
		newVote.setAlternative1(newVote.getAlternative1() + vote.getAlternative1());
		newVote.setAlternative2(newVote.getAlternative2() + vote.getAlternative2());
		poll.setVote(newVote);

		return pollRepository.save(poll);
	}

}