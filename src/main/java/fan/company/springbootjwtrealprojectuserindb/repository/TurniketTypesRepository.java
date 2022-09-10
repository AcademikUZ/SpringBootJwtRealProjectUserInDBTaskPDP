package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.TurniketTypes;
import fan.company.springbootjwtrealprojectuserindb.entity.TurniketTypes;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.TurniketNames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurniketTypesRepository extends JpaRepository<TurniketTypes, Long> {

    TurniketTypes findByTurniketNames(TurniketNames turniketNames);

}
