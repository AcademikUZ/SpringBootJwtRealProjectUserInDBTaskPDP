package fan.company.springbootjwtrealprojectuserindb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @NotNull
    @Email
    @Column(nullable = false)
    private String email;

    private String password;

    private String emailCode;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createAt; //user qachon yaratilganligini bildiradi

    @UpdateTimestamp
    private Timestamp updateAt;  //user qachon yangilanganligini bildiradi

   // @ManyToOne(fetch = FetchType.EAGER)  bunday qilsa Lazy yuklanganda kerak
    @ManyToOne
    private Role roles;     //userning rollari

    private boolean accountNonExpired = true; // userning amal qilish muddati o'tmagan

    private boolean accountNonLocked = true ; //user blocklanmaganligini bildiradi

    private boolean credentialsNonExpired = true;

    private boolean enabled = false; //user yoniqmi



    /**-------------Userdatailsni metodlari------------------**/


    //Userning huquqlari ro'yxati
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.roles);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //amal qilish muddati tugamaganligi
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    //Accaunt bloklanganligi holatini qaytaradi
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    //Accauntning ishonchlimi yoki yoqmi shuni qaytaradi
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    //Accauntning activligi
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
