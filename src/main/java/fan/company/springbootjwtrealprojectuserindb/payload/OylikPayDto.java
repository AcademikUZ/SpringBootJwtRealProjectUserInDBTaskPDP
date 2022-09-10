package fan.company.springbootjwtrealprojectuserindb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OylikPayDto {

    @NotNull
    private Long userId;

    private Double tolanganpul;  // agar null bo'lsa Oylik classidagi miqdor qiymatini oladi


}
