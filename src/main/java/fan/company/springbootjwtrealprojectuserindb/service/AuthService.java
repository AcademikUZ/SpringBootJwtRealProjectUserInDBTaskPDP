package fan.company.springbootjwtrealprojectuserindb.service;

import fan.company.springbootjwtrealprojectuserindb.entity.Turniket;
import fan.company.springbootjwtrealprojectuserindb.payload.PasswordDto;
import fan.company.springbootjwtrealprojectuserindb.security.PasswordValidator;
import fan.company.springbootjwtrealprojectuserindb.entity.enumes.RoleType;
import fan.company.springbootjwtrealprojectuserindb.payload.LoginDto;
import fan.company.springbootjwtrealprojectuserindb.repository.RoleRepository;
import fan.company.springbootjwtrealprojectuserindb.repository.UserRepository;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.RegisterDto;
import fan.company.springbootjwtrealprojectuserindb.security.tokenGenerator.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    public ApiResult register(RegisterDto registerDto) {
        try {
            if (registerDto.getRoleId() == 1) {
                return new ApiResult("Sizda bunday huquq yo'q", false);
            }


            User userInSystem = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (userInSystem != null) {
                if (userInSystem.getRoles().getId() >= registerDto.getRoleId()
                        || userInSystem.getRoles().getRoleName().equals(RoleType.ROLE_XODIM)

                ) {
                    return new ApiResult("Sizda bunday huquq yo'q", false);
                }
            } else{
                    return new ApiResult("Avval tizimga kiring", false);
            }

            if(userInSystem.getRoles().getId() ==1 && registerDto.getRoleId()>3){
                return new ApiResult("Direktor faqat managerlarni qo'sha oladi!", false);
            }

            boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
            if (existsByEmail)
                return new ApiResult("Bunday email mavjud", false);


            User user = new User();
            user.setFullName(registerDto.getFullName());
            user.setEmail(registerDto.getEmail());
            //user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setRoles(roleRepository.findById(registerDto.getRoleId()).get());
            user.setEmailCode(UUID.randomUUID().toString());
            User savedUser = userRepository.save(user);
            Boolean sendMail = sendMail(savedUser.getEmail(), savedUser.getEmailCode());

            if (sendMail)
                return new ApiResult("Muvoffaqiyatli saqlandi. Akkauntingiz aktivlatirish uchun emailingizni tasdiqlang " + savedUser.getEmail(), true);

            return new ApiResult("Email jo'natishda hatolik", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResult("Hatolik", false);
        }


    }


    public Boolean sendMail(String sendingEmail, String message) {

        String buttonStyle = "background-color: #1c87c9;\n" +
                "        border: none;\n" +
                "        color: white;\n" +
                "        padding: 20px 34px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "        font-size: 20px;\n" +
                "        margin: 4px 2px;\n" +
                "        cursor: pointer;";

        String button = "<input style=" + buttonStyle + " type='button' onclick='http://localhost:8080/api/auth/verifyEmail?emailCode=" + message + "&email=" + sendingEmail + "'; value='Tasdiqlash' />";
        String button1 = "<a style=" + buttonStyle + "  href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + message + "&email=" + sendingEmail + "'; value='Tasdiqlash' </a>";

        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("company@fan.uz");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("company@fan.uz tizimida accaountni tasdiqlash");
            simpleMailMessage.setText(button);
            System.out.println(button);
            javaMailSender.send(simpleMailMessage);
            return true;

        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }

    }


    public ApiResult verifyEmail(String emailCode, String email, PasswordDto password) {

        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (!optionalUser.isPresent())
            return new ApiResult("Hatolik", false);

        boolean passwordValidatorValid = passwordValidator.isValid(password.getPassword());

        if (!passwordValidatorValid)
            return new ApiResult("Password mustahkam emas", false);

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        user.setEnabled(true);
        user.setEmailCode(null);
        userRepository.save(user);
        return new ApiResult("Muvoffaqiyatli saqlandi", true);
    }


    public ApiResult loginUser(LoginDto loginDto) {
        try {

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
//
//            Optional<User> optionalUser = userRepository.findByEmail(loginDto.getUsername());
//            if (!optionalUser.isPresent()) {
//                return new ApiResult("Login yoki parol xato!", false);
//            }
//
//            if (!passwordEncoder.matches(loginDto.getPassword(), optionalUser.get().getPassword())) {
//                return new ApiResult("Login yoki parol xato!", false);
//            }


            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResult("Token", true, token);
        } catch (BadCredentialsException e) {
            return new ApiResult("Login yoki parol xato!", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * Usernameni email orqali topish
         */

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " topilmadi!"));

    }
    public Page<User> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userRepository.findAll(pageable);
    }
}
