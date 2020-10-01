package Projects.ProjectB;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IoTDeviceRepository extends CrudRepository<IotDevice, Long> {

    IotDevice findById(long id);

    List<IotDevice> findByPoll(Poll poll);
}
