package Projects.ProjectB.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IotDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Poll poll;

    @Override
    public String toString() {
        return String.format(
                "IoT Device[Id='%d']", id
        );
    }
}
