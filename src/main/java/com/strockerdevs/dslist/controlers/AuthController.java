package com.strockerdevs.dslist.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.repositories.PessoaRepository;
import com.strockerdevs.dslist.services.PasswordResetService;

@Controller
public class AuthController {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    // Tela de Login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Retorna a view de login
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String senha, Model model) {
        // Lógica de autenticação aqui
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        if (!passwordEncoder.matches(senha, pessoa.getSenha())) {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            return "login"; // Retorna à página de login com mensagem de erro
        }

        // Autenticação bem-sucedida
        return "redirect:/"; // Redireciona para a página inicial após o login
    }

    // Tela de Cadastro
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("pessoa", new Pessoa()); // Adiciona um objeto Pessoa ao modelo
        return "register"; // Retorna a view de cadastro
    }

    // Tela de Esqueci Minha Senha
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password"; // Retorna a view de esqueci minha senha
    }

    // Processar a solicitação de redefinição de senha
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        try {
            // Gera o token e envia o email
            String token = passwordResetService.createPasswordResetTokenForUser(email);

            // Redireciona para a página de redefinição de senha com o token na URL
            return "redirect:/reset-password?token=" + token;
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao enviar o email. Tente novamente.");
            return "forgot-password"; // Retorna à página com mensagem de erro
        }
    }

    // Tela de Redefinição de Senha
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam(required = false) String token, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Token de redefinição de senha não fornecido.");
            return "error"; // Retorna uma página de erro
        }
        model.addAttribute("token", token); // Passa o token para a view
        return "reset-password"; // Retorna a view de redefinição de senha
    }

    // Processar a redefinição de senha
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        boolean success = passwordResetService.resetPassword(token, newPassword);
        if (success) {
            model.addAttribute("message", "Senha redefinida com sucesso.");
            return "login"; // Redireciona para a página de login após sucesso
        } else {
            model.addAttribute("error", "Token inválido ou expirado.");
            return "reset-password"; // Retorna à página de redefinição com mensagem de erro
        }
    }
}