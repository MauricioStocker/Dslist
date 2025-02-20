package com.strockerdevs.dslist.config;

import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.entities.Pessoa.Role;
import com.strockerdevs.dslist.repositories.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário administrador já existe
            if (pessoaRepository.findByEmail("admin@example.com").isEmpty()) {
                Pessoa admin = new Pessoa();
                admin.setNome("Admin User");
                admin.setEmail("admin@example.com");
                admin.setSenha(passwordEncoder.encode("admin123")); // Senha criptografada
                admin.setRole(Role.ADMIN); // Define como ADMIN

                pessoaRepository.save(admin);
                System.out.println("Usuário administrador criado com sucesso.");
            } else {
                System.out.println("Usuário administrador já existe no banco de dados.");
            }
        };
    }
}