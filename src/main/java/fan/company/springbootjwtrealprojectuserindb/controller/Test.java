package fan.company.springbootjwtrealprojectuserindb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {



    public static void main(String[] args) {

        String password = "1122AA@11";
        System.out.println(passwordEncoder().encode(password));


    }

    static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
