package com.example.demo.config;

import com.example.demo.services.SecurityService.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;


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
                .antMatchers("/", "/login", "/register").permitAll()
                // URLs accessible to all authenticated users
                .antMatchers("/pacient/**").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/journalPages/**").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/specialist/getSpecialistByName/**").hasAnyRole( "ADMIN", "PACIENT", "SPECIALIST")
                .antMatchers("/specialist/getDoctorsList").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/specialist/getSpecialistByEmail/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/appointments/getAppPS/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/appointments/newApp").hasAnyRole( "ADMIN", "PACIENT")
                // URLs accessible only to specialists and admin
                .antMatchers("/journalPages/getpagesForSpecialist/*").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/specialist/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/appointments/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/reviews/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/payment/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/location/**").hasAnyRole("SPECIALIST", "ADMIN")
                // Any other URLs require authentication
                .anyRequest().authenticated()
        );
        http.userDetailsService(userDetailsService);
        http.headers((headers) -> headers.disable());
        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/perform_login")
        );
        http .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .permitAll()
        );
        http.exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));//                .httpBasic(Customizer.withDefaults())
        http .userDetailsService(userDetailsService)
             .csrf().disable(); // Disable CSRF protection for simplicity

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
// fara encoder
        return new BCryptPasswordEncoder();
    }

}