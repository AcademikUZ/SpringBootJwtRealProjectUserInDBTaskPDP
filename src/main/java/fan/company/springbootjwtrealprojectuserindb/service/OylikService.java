package fan.company.springbootjwtrealprojectuserindb.service;

import fan.company.springbootjwtrealprojectuserindb.entity.*;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.OylikDto;
import fan.company.springbootjwtrealprojectuserindb.payload.OylikPayDto;
import fan.company.springbootjwtrealprojectuserindb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OylikService {


    @Autowired
    OylikPayRepository oylikPayRepository;
    @Autowired
    OylikRepository oylikRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * Xodimlarda bergilangan oylikni barchasini qaytaradi
     * @param page
     * @return
     */
    public Page<Oylik> getAllOylik(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return oylikRepository.findAll(pageable);
    }

    /**
     * Xodimlarga tolab berilgan pullarni korsatadi
     * @param page
     * @return
     */
    public Page<OylikPay> getAllTolanganlar(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return oylikPayRepository.findAll(pageable);
    }

    /**
     * Userga to'langan barcha to'lovlarni chiqaradi
     * @param id
     * @return
     */

    public List<OylikPay> getByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        List<OylikPay> allByOylik_user = oylikPayRepository.findAllByOylik_User(optionalUser.get());
        return allByOylik_user;
    }

    /**
     * Vaqt orqligidagi to'lovlarni chiqaradi
     * @param start
     * @param end
     * @return
     */
    public List<OylikPay> getByBetweenTime(Timestamp start, Timestamp end) {

        List<OylikPay> allByTolanganvaqtiBetween = oylikPayRepository.findAllByTolanganvaqtiBetween(start, end);
        return allByTolanganvaqtiBetween;
    }

    /**
     * Vaqt oraligidagi ma'lum bir userga to'langan pullarni ko'rsatadi
     * @param start
     * @param end
     * @param userId
     * @return
     */
    public List<OylikPay> getByBetweenTimeAndUserId(Timestamp start, Timestamp end, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        List<OylikPay> allByTolanganvaqtiBetweenAndOylik_user = oylikPayRepository.findAllByTolanganvaqtiBetweenAndOylik_User(start, end, optionalUser.get());
        return allByTolanganvaqtiBetweenAndOylik_user;
    }


    public ApiResult payOylik (OylikPayDto dto) {

        try {
            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
            if (!optionalUser.isPresent())
                return new ApiResult("Bunday user mavjud emas!", false);
            Optional<Oylik> optionalOylik = oylikRepository.findByUser(optionalUser.get());
            if (!optionalOylik.isPresent())
                return new ApiResult("Userga oylik belgilanmagan!", false);

            OylikPay oylikPay = new OylikPay();
            oylikPay.setOylik(optionalOylik.get());
            if(dto.getTolanganpul() != null) {
                oylikPay.setTolanganpul(dto.getTolanganpul());
            } else {
                oylikPay.setTolanganpul(optionalOylik.get().getOylikmiqdori());
            }
            oylikPayRepository.save(oylikPay);
            return new ApiResult("Muvoffaqiyatli saqlandi!", true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResult("Hatolik!", false);
        }
    }

    public ApiResult oylikBelgilash(OylikDto dto) {

        try {
            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
            if (!optionalUser.isPresent())
                return new ApiResult("Bunday user mavjud emas!", false);
            Optional<Oylik> optionalOylik = oylikRepository.findByUser(optionalUser.get());
            if (optionalOylik.isPresent())
                return new ApiResult("Userga oylik belgilangan!", false);

            Oylik oylik = new Oylik();
            oylik.setOylikmiqdori(dto.getBelgilanganoylik());
            oylik.setUser(optionalUser.get());
            oylikRepository.save(oylik);
            return new ApiResult("Muvoffaqiyatli saqlandi!", true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResult("Hatolik!", false);
        }
    }

    public ApiResult oylikniTaxrirlash(OylikDto dto) {

        try {
            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
            if (!optionalUser.isPresent())
                return new ApiResult("Bunday user mavjud emas!", false);
            Optional<Oylik> optionalOylik = oylikRepository.findByUser(optionalUser.get());
            if (!optionalOylik.isPresent())
                return new ApiResult("Userga oylik belgilanmagan!", false);

            Oylik oylik = optionalOylik.get();
            oylik.setOylikmiqdori(dto.getBelgilanganoylik());
            oylikRepository.save(oylik);
            return new ApiResult("Muvoffaqiyatli saqlandi!", true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResult("Hatolik!", false);
        }
    }

    public ApiResult deleteOylik(Long id) {
        Optional<Oylik> optional = oylikRepository.findById(id);
        if (!optional.isPresent()) {
            return new ApiResult("Tanlangan oylik mavjud emas.", false);
        }
        oylikRepository.deleteById(id);
        return new ApiResult("Muvoffaqaiyatli o'chirildi!", true);
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
