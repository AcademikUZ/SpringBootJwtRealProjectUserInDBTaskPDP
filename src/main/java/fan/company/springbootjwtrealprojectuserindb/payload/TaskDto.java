package fan.company.springbootjwtrealprojectuserindb.payload;

import fan.company.springbootjwtrealprojectuserindb.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {

    @NotNull
    private String name;

    @NotNull
    private Long userID;

    private String izoh;

    private Date dateFinish;


}
