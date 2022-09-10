package fan.company.springbootjwtrealprojectuserindb.entity;

import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.TurniketNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TurniketTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TurniketNames turniketNames;
}
