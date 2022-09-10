package fan.company.springbootjwtrealprojectuserindb.repository;


import fan.company.springbootjwtrealprojectuserindb.entity.OylikPay;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;


public interface OylikPayRepository extends JpaRepository<OylikPay, Long> {


    List<OylikPay> findAllByTolanganvaqtiBetween(Timestamp start, Timestamp end);

    List<OylikPay> findAllByTolanganvaqtiBetweenAndOylik_User(Timestamp start, Timestamp end, User oylik_user);

    List<OylikPay> findAllByOylik_User(User oylik_user);




}
