package com.petrbambas.dms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
    for simplicity basic auth using BCrypt encryption
    roles and users defined in this class
    roles and users could be saved in DB alternatively (users, users_roles, roles)
    */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails petr = User.builder()
                .username("petr")
                .password(passwordEncoder().encode("Rolp4/47*-9"))
                .roles("ADMIN")
                .build();

        UserDetails klara = User.builder()
                .username("klara")
                .password(passwordEncoder().encode("$2aGkgzT+E5RFUbg"))
                .roles("ASSISTANT")
                .build();
        return new InMemoryUserDetailsManager(petr, klara);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/protocols").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/protocols/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/protocols").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/protocols").hasRole("ASSISTANT")
                        .requestMatchers(HttpMethod.PUT, "/api/protocols/status/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/documents").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/documents").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/documents/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/documents/**").hasRole("ADMIN"));
        // Use HTTP Basic auth
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());
        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}