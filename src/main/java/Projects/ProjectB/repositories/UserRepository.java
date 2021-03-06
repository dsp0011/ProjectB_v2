package Projects.ProjectB.repositories;

import Projects.ProjectB.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, String> {

    User findByUserName(String userName);
    User findById(long id);
    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);
}
