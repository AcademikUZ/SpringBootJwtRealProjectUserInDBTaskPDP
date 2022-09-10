package fan.company.springbootjwtrealprojectuserindb.security.config;

import fan.company.springbootjwtrealprojectuserindb.security.PasswordValidator;
import fan.company.springbootjwtrealprojectuserindb.security.JwtFilter;
import fan.company.springbootjwtrealprojectuserindb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;
    @Autowired
    JwtFilter jwtFilter;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    PasswordValidator passwordValidator(){
        return new PasswordValidator();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/api/auth/register",
//                                        "/api/auth/verifyEmail",
//                                        "/api/auth/login"
//                ).permitAll()
                .antMatchers("/api/auth/login", "/api/auth/verifyEmail").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //filterni o'rnatish uchun kerak
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Buni WebSecurityConfigurerAdapter ni o'rniga ishlatadi
     */

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                        .csrf().disable()
//                        .httpBasic().disable()
//                        .authorizeRequests()
//                        .antMatchers("/api/auth/register").permitAll()
//                        .anyRequest().authenticated();
//
//        return http.build();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
