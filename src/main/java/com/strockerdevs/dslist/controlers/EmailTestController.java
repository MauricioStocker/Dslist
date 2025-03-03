package com.strockerdevs.dslist.controlers;

import com.strockerdevs.dslist.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestParam String name,
            @RequestParam String from,
            @RequestParam String subject,
            @RequestParam String text) {
        if (name == null || name.isEmpty() ||
                from == null || from.isEmpty() ||
                subject == null || subject.isEmpty() ||
                text == null || text.isEmpty()) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios.");
        }

        try {
            String siteEmail = "stocker.caicara@gmail.com";
            String fullMessage = "Mensagem enviada por: " + name + " (" + from + ")\n\n" + text;

            emailService.sendEmail(siteEmail, subject, fullMessage, name, from);
            return ResponseEntity.ok("E-mail enviado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar o e-mail. Por favor, tente novamente.");
        }
    }
}