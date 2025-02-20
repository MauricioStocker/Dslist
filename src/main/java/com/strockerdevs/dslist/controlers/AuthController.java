package com.strockerdevs.dslist.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.strockerdevs.dslist.entities.Pessoa;

@Controller
public class AuthController {

    // Tela de Login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Retorna a view de login
    }

    // Tela de Cadastro
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("pessoa", new Pessoa()); // Adiciona um objeto Pessoa ao modelo
        return "register"; // Retorna a view de cadastro
    }
}