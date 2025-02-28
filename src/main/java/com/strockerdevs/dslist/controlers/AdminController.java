package com.strockerdevs.dslist.controlers;

import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private PessoaService pessoaService;

    // Exibir a página de cadastro de administradores
    @GetMapping("/admin/create-admin")
    public String showCreateAdminPage() {
        return "create-admin"; // Retorna a view create-admin.html
    }

    // Criar um novo administrador
    @PostMapping("/admin/create-admin")
    public String createAdmin(Pessoa pessoa, RedirectAttributes redirectAttributes) {
        try {
            pessoa.setRole(Pessoa.Role.ADMIN); // Define a role como ADMIN
            pessoaService.cadastrarPessoa(pessoa);
            redirectAttributes.addFlashAttribute("successMessage", "Novo administrador criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar administrador.");
        }
        return "redirect:/admin/dashboard";
    }

    // Exibir o dashboard de administradores
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        List<Pessoa> admins = pessoaService.listAllAdmins(); // Obtém todos os administradores
        model.addAttribute("admins", admins);
        return "admin-dashboard"; // Retorna a view admin-dashboard.html
    }

    // Excluir um administrador
    @PostMapping("/admin/delete-admin")
    public String deleteAdmin(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.deleteAdminById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Administrador excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir administrador.");
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/user-dashboard")
    public String userDashboard(Model model) {
        // Buscar todos os usuários com role USER
        List<Pessoa> users = pessoaService.listAllUsers();
        model.addAttribute("users", users);
        return "user-dashboard";
    }
}