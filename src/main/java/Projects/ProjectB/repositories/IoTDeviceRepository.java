package Projects.ProjectB.repositories;

import Projects.ProjectB.entities.IotDevice;
import Projects.ProjectB.entities.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IoTDeviceRepository extends CrudRepository<IotDevice, Long> {

    IotDevice findById(long id);

    List<IotDevice> findByPoll(Poll poll);
}
