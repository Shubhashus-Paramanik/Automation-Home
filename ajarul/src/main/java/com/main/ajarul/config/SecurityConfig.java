package com.main.ajarul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.main.ajarul.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
private JwtAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        // http
        //     .csrf(csrf -> csrf.disable())
        //     .authorizeHttpRequests(auth -> auth
        //             .anyRequest()  //this temporarily disable the authentication so you can text your apis
        //             .permitAll());

        http
        .cors(cors->{})
        .csrf(csrf->csrf.disable())
        .sessionManagement(
            session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        // .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/login","/api/auth/register")
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/login","/api/auth/register","/api/esp32/**","/ws/**")

            .permitAll().anyRequest().authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
