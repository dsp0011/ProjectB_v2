package Projects.ProjectB;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Long> {

    Poll findById(long id);

    List<Poll> findByIsPublic(boolean isPublic);

    List<Poll> findByIsActive(boolean isActive);

    List<Poll> findByCreator(User creator);

}
