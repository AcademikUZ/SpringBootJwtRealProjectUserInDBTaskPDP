package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.Role;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(RoleType roleName);

    boolean existsByRoleName(RoleType roleName);

}
