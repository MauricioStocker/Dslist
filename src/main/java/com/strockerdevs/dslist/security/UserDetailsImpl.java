package com.strockerdevs.dslist.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String email, String senha, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.senha = senha;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha; // Certifique-se de que a senha está sendo retornada corretamente
    }

    @Override
    public String getUsername() {
        return email; // O email é usado como nome de usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}