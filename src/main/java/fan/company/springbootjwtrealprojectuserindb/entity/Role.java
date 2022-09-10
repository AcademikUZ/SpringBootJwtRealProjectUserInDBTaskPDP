package fan.company.springbootjwtrealprojectuserindb.entity;

import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleName;


    @Override
    public String getAuthority() {
        return this.roleName.name();
    }
}
