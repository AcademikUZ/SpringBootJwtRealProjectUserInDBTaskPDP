package fan.company.springbootjwtrealprojectuserindb.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {

    @Size(min = 1)
    @NotNull
    private String fullName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Long roleId;


}
