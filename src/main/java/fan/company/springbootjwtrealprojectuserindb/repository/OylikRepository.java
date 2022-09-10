package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.Oylik;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OylikRepository extends JpaRepository<Oylik, Long> {

    Optional<Oylik> findByUser(User user);
}
