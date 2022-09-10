package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.Tasks;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<Tasks, Long> {

    boolean existsByName(String name);

    List<Tasks> findAllByUser(User user);

    boolean existsByIdAndUser(Long id, User user);

    List<Tasks> findAllByDateFinishBefore(Date dateFinish);

}
