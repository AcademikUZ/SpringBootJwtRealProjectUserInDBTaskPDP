package fan.company.springbootjwtrealprojectuserindb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OylikDto {

    @NotNull
    private Long userId;

    @NotNull
    private Double belgilanganoylik;


}
