package com.strockerdevs.dslist.services;

import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.repositories.PessoaRepository;
import com.strockerdevs.dslist.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  PessoaRepository pessoaRepository;

    public UserDetailsServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new UserDetailsImpl(
                pessoa.getEmail(),
                pessoa.getSenha(), // Certifique-se de que a senha está criptografada
                Collections.singleton(() -> "ROLE_" + pessoa.getRole().name())
        );
    }
}
