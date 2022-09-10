package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.Turniket;
import fan.company.springbootjwtrealprojectuserindb.entity.TurniketTypes;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TurniketRepository extends JpaRepository<Turniket, Long> {

       List<Turniket> findAllByUser(User user);

       List<Turniket> findAllByTurniketTypes(TurniketTypes turniketTypes);

       List<Turniket> findAllByVaqtiBetween(Timestamp start, Timestamp end);

}
