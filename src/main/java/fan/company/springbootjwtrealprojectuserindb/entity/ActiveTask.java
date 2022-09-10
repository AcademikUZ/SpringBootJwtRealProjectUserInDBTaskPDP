package fan.company.springbootjwtrealprojectuserindb.entity;

import fan.company.springbootjwtrealprojectuserindb.entity.enumes.ActiveType;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ActiveTask {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActiveType activeType;

}
