package com.petdoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Abordagem TEMPORÁRIA para facilitar o desenvolvimento dos formulários.
                // Remove a necessidade de enviar o token CSRF a cada requisição.
                .csrf(csrf -> csrf.disable())

                /*
                // Abordagem DEFINITIVA e SEGURA (usar esta no futuro)
                // Quando habilitada, todos os formulários POST precisarão incluir o token CSRF.
                // O Thymeleaf faz isso automaticamente se a dependência thymeleaf-extras-springsecurity6 estiver no pom.xml
                // e o formulário contiver um campo oculto para o token.
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // Exemplo: ignorar CSRF para futuras APIs REST
                */

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/login", "/cadastro").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}
