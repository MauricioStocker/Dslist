package com.strockerdevs.dslist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Configura o PasswordEncoder
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                // Rotas públicas
                .requestMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/forgot-password",
                        "/reset-password",
                        "/css/**",
                        "/js/**",
                        "/h2-console/**",
                        "/videos/**",
                        "/images/**",
                        "/send-email" // Adicionado aqui para permitir acesso público
                )
                .permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN") // Apenas ADMIN pode acessar /admin/**
                .requestMatchers("/games/add").hasRole("ADMIN") // Apenas ADMIN pode adicionar jogos
                .requestMatchers("/games/**").permitAll() // Outras páginas de jogos são públicas
                .anyRequest().authenticated() // Todas as outras rotas exigem autenticação
                )
                .formLogin(form -> form
                .loginPage("/login") // Página personalizada de login
                .defaultSuccessUrl("/", true) // Redireciona para a home após o login
                .permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/?logout") // Redireciona para a home após o logout
                .permitAll()
                )
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**", "/login", "/send-email") // Ignora CSRF para o console H2 e /send-email
                )
                .headers(headers -> headers.frameOptions().disable()); // Desabilita proteção de frame para o H2

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
