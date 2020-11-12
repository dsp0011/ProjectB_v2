package Projects.ProjectB;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Long> {

    Poll findById(long id);

    List<Poll> findByIsPublic(Boolean isPublic);

    List<Poll> findByIsActive(Boolean isActive);

    List<Poll> findByCreator(User creator);

    List<Poll> findByUsersVoted(User user);

}
