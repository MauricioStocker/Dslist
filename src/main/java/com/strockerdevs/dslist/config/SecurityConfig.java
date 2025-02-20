package com.strockerdevs.dslist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Configura o PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador de senhas BCrypt
    }

    // Configura o AuthenticationManager usando o AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configura o SecurityFilterChain para definir regras de acesso
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Rotas públicas (acessíveis sem autenticação)
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/h2-console/**","/videos/**","/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN") // Apenas ADMIN pode acessar /admin/**
                .requestMatchers("/games/add").hasRole("ADMIN") // Apenas ADMIN pode adicionar jogos
                .requestMatchers("/games/**").permitAll()       // Outras páginas de jogos são públicas
                .anyRequest().authenticated()                  // Todas as outras rotas exigem autenticação
            )
            .formLogin(form -> form
                .loginPage("/login")                           // Página personalizada de login
                .defaultSuccessUrl("/", true)                  // Redireciona para a home após o login
                .permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/?logout")                  // Redireciona para a home após o logout
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")     // Ignora CSRF para o console H2
            )
            .headers(headers -> headers.frameOptions().disable()); // Desabilita proteção de frame para o H2

        return http.build();
    }
}