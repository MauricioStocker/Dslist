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

        // Verifica se o usuário é ADMIN
        boolean isAdmin = isAuthenticated && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "home"; // Retorna a view home.html
    }
}