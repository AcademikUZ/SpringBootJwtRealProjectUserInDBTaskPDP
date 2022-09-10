package fan.company.springbootjwtrealprojectuserindb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tasks {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String izoh;

    private Date dateFinish;

    @ManyToOne
    private ActiveTask activeTask;

    @CreatedBy
    @Column(updatable = false)
    private Long createby;  //taskni kim qo'shganligi

    @LastModifiedBy
    private Long updateby;  //taskni kim update qilganligi

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @ManyToOne
    private User user;

}
