package Projects.ProjectB;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {

@Autowired
private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userRepository.save(user);
        return "saved";
    }

	@GetMapping("/getUsers")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

    @PostMapping("/createPoll")
	public Poll createPoll(@RequestParam int timeLimit,
						   @RequestParam Boolean isPublic,
						   @RequestParam Boolean isActive,
						   @RequestParam User creator,
						   @RequestParam Vote vote) { // Vote ?

    	return new Poll(timeLimit, isPublic, isActive, creator, vote);
	}

	@PostMapping("/vote")
	public Vote createVote(@RequestBody Vote vote) {
    	return vote;
	}




//	@GetMapping("/greeting")
//	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return new Greeting(counter.incrementAndGet(), String.format(template, name));
//	}
}