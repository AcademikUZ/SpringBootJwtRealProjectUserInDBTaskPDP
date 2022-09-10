package fan.company.springbootjwtrealprojectuserindb.service;

import fan.company.springbootjwtrealprojectuserindb.entity.Role;
import fan.company.springbootjwtrealprojectuserindb.entity.Tasks;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.ActiveType;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.TaskDto;
import fan.company.springbootjwtrealprojectuserindb.repository.ActiveTaskRepository;
import fan.company.springbootjwtrealprojectuserindb.repository.RoleRepository;
import fan.company.springbootjwtrealprojectuserindb.repository.TaskRepository;
import fan.company.springbootjwtrealprojectuserindb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {


    @Autowired
    TaskRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ActiveTaskRepository activeTaskRepository;
    @Autowired
    JavaMailSender javaMailSender;


    public ApiResult edit(Long id, TaskDto dto) {

        User userInSystem = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean existsByIdAndUser = repository.existsByIdAndUser(id, userInSystem);
        if (!existsByIdAndUser) {
            return new ApiResult("Sizda bunday huquq mavjud emas!", false);
        }

        Optional<Tasks> optionalTasks = repository.findById(id);
        if (!optionalTasks.isPresent()) {
            return new ApiResult("Tanlangan task mavjud emas.", false);
        }

        Tasks editTasks = optionalTasks.get();
        editTasks.setName(dto.getName());
        editTasks.setIzoh(dto.getIzoh());
        editTasks.setDateFinish(dto.getDateFinish());
        editTasks.setActiveTask(activeTaskRepository.findByActiveType(ActiveType.TUGALLANGAN));
        editTasks.setUser(userInSystem);
        Tasks save = repository.save(editTasks);
        System.out.println(save);

        Optional<User> userOptional = userRepository.findById(save.getCreateby());
        Boolean sendMail = false;

        if (userOptional.isPresent())
            sendMail = sendMail(userOptional.get().getEmail(), "Sizga yangi vazifa biriktirildi!");

        if (sendMail)
            return new ApiResult("Muvoffaqiyatli tahrirlandi. Emailga habar yuborildi " + save.getUser().getEmail(), true);

        return new ApiResult("Hatolik!", false);

    }

    public Page<Tasks> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public List<Tasks> getAllMyTask() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findAllByUser(user);
    }

    public List<Tasks> getAllByUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent())
            return new ArrayList<>();
        return repository.findAllByUser(optionalUser.get());
    }

    public Tasks getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {
        Optional<Tasks> optional = repository.findById(id);
        if (!optional.isPresent()) {
            return new ApiResult("Tanlangan task mavjud emas.", false);
        }
        repository.deleteById(id);
        return new ApiResult("Muvoffaqaiyatli o'chirildi!", true);
    }

    public List<Tasks> getMuddatiOtibKetganVazifalar(){
        List<Tasks> allByDateFinishBefore = repository.findAllByDateFinishBefore(new Date());
        return allByDateFinishBefore;
    }

    public ApiResult add(TaskDto dto) {

        User userInSystem = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Role> optionalRole = roleRepository.findById(userInSystem.getRoles().getId());
        Role role = optionalRole.get();
        Optional<User> userOptional = userRepository.findById(dto.getUserID());
        if (!userOptional.isPresent())
            return new ApiResult("Bunday user mavjud emas!", false);

        if (userInSystem.getRoles().getId() >= userOptional.get().getRoles().getId()
            || userInSystem.getRoles().getRoleName().equals(RoleType.ROLE_XODIM)
        ) {
            return new ApiResult("Sizda bunday huquq yo'q!", false);
        }

        boolean existsByName = repository.existsByName(dto.getName());

        User user = userOptional.get();

        Tasks tasks = new Tasks();
        tasks.setName(dto.getName());
        tasks.setIzoh(dto.getIzoh());
        tasks.setDateFinish(dto.getDateFinish());
        tasks.setActiveTask(activeTaskRepository.findByActiveType(ActiveType.YANGI));
        tasks.setUser(user);
        Tasks save = repository.save(tasks);
        System.out.println(save);

        Boolean sendMail = sendMail(save.getUser().getEmail(), "Sizga yangi vazifa biriktirildi!");

        if (sendMail)
            return new ApiResult("Muvoffaqiyatli saqlandi. Emailga habar yuborildi " + save.getUser().getEmail(), true);

        return new ApiResult("Hatolik!", false);
    }


    public Boolean sendMail(String sendingEmail, String message) {

        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("company@fan.uz");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("company@fan.uz sizga habar biriktirildi");
            simpleMailMessage.setText(message);
            System.out.println(message);
            javaMailSender.send(simpleMailMessage);
            return true;

        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }

    }

}
