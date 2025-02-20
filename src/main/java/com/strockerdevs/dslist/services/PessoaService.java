package com.strockerdevs.dslist.services;

import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.repositories.PessoaRepository;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;

    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        if (pessoa.getRole() == null) {
            pessoa.setRole(Pessoa.Role.USER); // Define a role como USER por padrão
        }
        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        return pessoaRepository.save(pessoa);
    }
    public void deleteAdminById(Long id) {
        pessoaRepository.deleteById(id); // Exclui um administrador pelo ID
    }
    public List<Pessoa> listAllAdmins() {
        return pessoaRepository.findByRole(Pessoa.Role.ADMIN); // Lista todos os administradores
    }
}