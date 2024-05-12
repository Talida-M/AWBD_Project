package com.example.demo.config;

import com.example.demo.services.SecurityService.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final DataSource dataSource;
    private final JpaUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(DataSource dataSource, JpaUserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests
                // Public URLs
                .antMatchers("/", "/webjars/**", "/css/**", "/js/**","/doctors3.jpeg", "/images/**", "/images/*",  "/home",  "/register/patient", "/register/specialist", "/register/signUpP", "/register/signUpS", "/perform_login", "/pacient","appointment", "/appointment/form",  "/login", "/register").permitAll()
                // URLs accessible to all authenticated users
                .antMatchers("/pacient/**").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/pacient//delete/*").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/journalPages/newpage").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/journalPages/getpages/*").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/journalPages/changestatus/**").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/appointment/form").hasAnyRole(  "PACIENT")
                .antMatchers("/specialist").hasAnyRole( "PACIENT")
                .antMatchers("/specialist/getSpecialistByName/**").hasAnyRole( "SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/appointment/getAppPS/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/specialist/getDoctorsList").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/specialist/getSpecialistByEmail/*").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/appointment/appointmentList/**").hasAnyRole("SPECIALIST",  "PACIENT")
                .antMatchers("/appointment/appointmentList/*").hasAnyRole("SPECIALIST",  "PACIENT")
                .antMatchers("/appointment/newApp").hasAnyRole( "ADMIN", "PACIENT")
                .antMatchers("/appointment/add").hasAnyRole( "ADMIN", "PACIENT")
                .antMatchers("/appointment/status-selection").hasAnyRole( "SPECIALIST", "PACIENT")
                // URLs accessible only to specialists and admin
                .antMatchers("/specialist/deleteS/*").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/journalPages/getpagesForSpecialist/*").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/specialist/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/specialist/signUpS").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/pacient/getPacients").hasAnyRole("SPECIALIST")
                .antMatchers("/appointment/getAppPD/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/appointment/getAppPS/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/appointment/delete/*").hasAnyRole("SPECIALIST",  "PACIENT")
                .antMatchers("/reviews/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/reviews/delete/*").hasAnyRole( "ADMIN", "PACIENT")
                .antMatchers("/reviews/newReview").hasAnyRole("ADMIN", "PACIENT")
                .antMatchers("/payment/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/location/**").hasAnyRole("SPECIALIST", "ADMIN")
                // Any other URLs require authentication
                .anyRequest().authenticated()
        );

        http.userDetailsService(userDetailsService)
                .headers((headers) -> headers.disable());
        http
                .formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/perform_login")
                        .permitAll()
        )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
// fara encoder
        return new BCryptPasswordEncoder();
    }

}
