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
                .antMatchers("/journalPages/**").hasAnyRole("PACIENT", "ADMIN")
                .antMatchers("/appointment/form").hasAnyRole(  "PACIENT")
                .antMatchers("/specialist").hasAnyRole( "PACIENT")
                .antMatchers("/specialist/getSpecialistByName/**").hasAnyRole( "ADMIN", "PACIENT", "SPECIALIST")
                .antMatchers("/specialist/getDoctorsList").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/specialist/getSpecialistByEmail/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/appointment/getAppPS/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/appointment/appointmentList/**").hasAnyRole("SPECIALIST",  "PACIENT")
                .antMatchers("/appointment/appointmentList/*").hasAnyRole("SPECIALIST",  "PACIENT")
                .antMatchers("/appointment/newApp").hasAnyRole( "ADMIN", "PACIENT")
                .antMatchers("/appointment/add").hasAnyRole( "ADMIN", "PACIENT")

                .antMatchers("/appointment/status-selection").hasAnyRole( "SPECIALIST", "PACIENT")

                // URLs accessible only to specialists and admin
                .antMatchers("/journalPages/getpagesForSpecialist/*").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/specialist/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/pacient").hasAnyRole("SPECIALIST")
                .antMatchers("/appointment/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/reviews/**").hasAnyRole("SPECIALIST", "ADMIN", "PACIENT")
                .antMatchers("/payment/**").hasAnyRole("SPECIALIST", "ADMIN")
                .antMatchers("/location/**").hasAnyRole("SPECIALIST", "ADMIN")
                // Any other URLs require authentication
                .anyRequest().authenticated()
        );

        http.userDetailsService(userDetailsService)
                .headers((headers) -> headers.disable());
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
        http
                .formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/perform_login")
                        .permitAll()
        )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));;
        http .logout(logout -> logout
                .logoutUrl("/perform_logout")
                .permitAll()
        );
//        http.exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));//                .httpBasic(Customizer.withDefaults())
//        http .userDetailsService(userDetailsService)
//             .csrf().disable(); // Disable CSRF protection for simplicity

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
// fara encoder
        return new BCryptPasswordEncoder();
    }

}
