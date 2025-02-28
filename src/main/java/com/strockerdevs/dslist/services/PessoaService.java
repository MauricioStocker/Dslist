package com.strockerdevs.dslist.services;

import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.repositories.PessoaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        if (pessoa.getRole() == null) {
            pessoa.setRole(Pessoa.Role.USER);
        }
        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        return pessoaRepository.save(pessoa);
    }

    public void deleteAdminById(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<Pessoa> listAllAdmins() {
        return pessoaRepository.findByRole(Pessoa.Role.ADMIN);
    }

    public Optional<Pessoa> findByEmail(String email) {
        return pessoaRepository.findByEmail(email);
    }

    // Novo método para listar todos os usuários
    public List<Pessoa> listAllUsers() {
        return pessoaRepository.findByRole(Pessoa.Role.USER);
    }
}
