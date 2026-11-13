package com.telusko.security_2.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider dauAuth = new DaoAuthenticationProvider(userDetailsService);
//        dauAuth.setUserDetailsService();
//        dauAuth.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        dauAuth.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return dauAuth;
    }

//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("kapil")
//                .password("kapiljava")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("Shiva")
//                .password("adminshiva")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
