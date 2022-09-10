package fan.company.springbootjwtrealprojectuserindb.service;

import fan.company.springbootjwtrealprojectuserindb.entity.*;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.ActiveType;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.TaskDto;
import fan.company.springbootjwtrealprojectuserindb.payload.TurniketDto;
import fan.company.springbootjwtrealprojectuserindb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TurniketService {


    @Autowired
    TurniketRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TurniketTypesRepository turniketTypesRepository;


    public Page<Turniket> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public List<Turniket> getByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ArrayList<>();
        return repository.findAllByUser(optionalUser.get());
    }

    public List<Turniket> getByTurniketType(Long id) {
        Optional<TurniketTypes> optionalTurniketTypes = turniketTypesRepository.findById(id);
        if (!optionalTurniketTypes.isPresent())
            return new ArrayList<>();
        return repository.findAllByTurniketTypes(optionalTurniketTypes.get());
    }

    public List<Turniket> getByTCreateTime(Timestamp start, Timestamp end) {
        return repository.findAllByVaqtiBetween(start, end);
    }


    public ApiResult add(TurniketDto dto) {

        try {

            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
            if (!optionalUser.isPresent())
                return new ApiResult("Bunday user mavjud emas!", false);

            Optional<TurniketTypes> optionalTurniketTypes = turniketTypesRepository.findById(dto.getTurniket_types());
            if (!optionalTurniketTypes.isPresent())
                return new ApiResult("Bunday turniket type mavjud emas!", false);

            Turniket turniket = new Turniket();
            turniket.setUser(optionalUser.get());
            turniket.setTurniketTypes(optionalTurniketTypes.get());
            repository.save(turniket);
            return new ApiResult("Muvoffaqiyatli saqlandi!", true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResult("Hatolik!", false);
        }

    }


//    public Boolean sendMail(String sendingEmail, String message) {
//
//        try {
//
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom("company@fan.uz");
//            simpleMailMessage.setTo(sendingEmail);
//            simpleMailMessage.setSubject("company@fan.uz sizga habar biriktirildi");
//            simpleMailMessage.setText(message);
//            System.out.println(message);
//            javaMailSender.send(simpleMailMessage);
//            return true;
//
//        } catch (MailException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }

}
