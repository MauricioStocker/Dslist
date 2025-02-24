package com.strockerdevs.dslist.controlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Exibir a página inicial
    @GetMapping("/")
    public String showHomePage(Model model) {
        // Obter informações de autenticação
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário está autenticado
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser");

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            // Adiciona o nome do usuário ao modelo
            String username = authentication.getName();
            model.addAttribute("username", username);

            // Verifica se o usuário é ADMIN
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);

            // Adiciona a role do usuário ao modelo
            String role = isAdmin ? "ADMIN" : "USER";
            model.addAttribute("role", role);
        }

        return "home"; // Retorna a view home.html
    }
}
