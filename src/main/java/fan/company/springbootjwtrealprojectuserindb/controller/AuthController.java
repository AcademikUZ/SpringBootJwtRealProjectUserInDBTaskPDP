package fan.company.springbootjwtrealprojectuserindb.controller;

import fan.company.springbootjwtrealprojectuserindb.entity.Turniket;
import fan.company.springbootjwtrealprojectuserindb.entity.User;
import fan.company.springbootjwtrealprojectuserindb.payload.ApiResult;
import fan.company.springbootjwtrealprojectuserindb.payload.LoginDto;
import fan.company.springbootjwtrealprojectuserindb.payload.PasswordDto;
import fan.company.springbootjwtrealprojectuserindb.payload.RegisterDto;
import fan.company.springbootjwtrealprojectuserindb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto userDto){
        ApiResult apiResult = service.register(userDto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    @PostMapping("/verifyEmail")
    public HttpEntity<?> emailOrqaliTasdiqlash(@RequestParam String emailCode,
                                               @RequestParam String email, @RequestBody PasswordDto password){
        ApiResult apiResult = service.verifyEmail(emailCode, email, password);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(apiResult);
    }


    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody LoginDto loginDto){
        ApiResult apiResult = service.loginUser(loginDto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.OK:HttpStatus.UNAUTHORIZED).body(apiResult);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_DIRECTOR', 'ROLE_HR_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page) {
        Page<User> all = service.getAll(page);
        return ResponseEntity.status(all.hasContent() ? HttpStatus.OK : HttpStatus.CONFLICT).body(all);
    }

}
