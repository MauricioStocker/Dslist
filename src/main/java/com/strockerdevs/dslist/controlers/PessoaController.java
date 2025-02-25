package com.strockerdevs.dslist.controlers;

import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    // Processa o formulário de cadastro
    @PostMapping("/register")
    public String registerPessoa(Pessoa pessoa, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.cadastrarPessoa(pessoa); // Salva a pessoa no banco de dados
            redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
            return "redirect:/login"; // Redireciona para a página de login
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar usuário.");
            return "redirect:/register"; // Redireciona de volta para a página de cadastro
        }
    }
}