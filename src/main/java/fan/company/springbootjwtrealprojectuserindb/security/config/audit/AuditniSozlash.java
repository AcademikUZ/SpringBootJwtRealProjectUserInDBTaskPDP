package fan.company.springbootjwtrealprojectuserindb.security.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class AuditniSozlash {

    @Bean
    public AuditorAware<Long> auditorProvider(){
        return new KimYozganiniAuditQilish();
    }

}
