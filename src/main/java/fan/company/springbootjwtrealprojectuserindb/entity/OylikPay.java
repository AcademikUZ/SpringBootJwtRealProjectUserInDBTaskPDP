package fan.company.springbootjwtrealprojectuserindb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OylikPay {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Timestamp tolanganvaqti;

    @ManyToOne
    private Oylik oylik;

    private Double tolanganpul;

}
