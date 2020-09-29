package Projects.ProjectB;

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
private PollRepossitory pollRepository;


/*
			USER REQUESTS
 */
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "User saved";
    }

	@GetMapping("/getUsers")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}


/*
			POLL REQUESTS
 */

    @PostMapping("/createPoll")
	public String createPoll(@RequestBody Poll poll) {
		pollRepository.save(poll);
		return "User saved";
	}

	@GetMapping("/getPolls")
	public @ResponseBody Iterable<Poll> getAllPolls() {
		// This returns a JSON or XML with the users
		return pollRepository.findAll();
	}

}