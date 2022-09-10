package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.ActiveTask;
import fan.company.springbootjwtrealprojectuserindb.entity.Role;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.ActiveType;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveTaskRepository extends JpaRepository<ActiveTask, Long> {

    ActiveTask findByActiveType(ActiveType activeType);

}
