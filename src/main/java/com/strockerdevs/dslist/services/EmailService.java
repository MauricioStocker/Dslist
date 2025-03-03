package com.strockerdevs.dslist.services;

import com.strockerdevs.dslist.entities.SentEmail;
import com.strockerdevs.dslist.repositories.SentEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SentEmailRepository sentEmailRepository;

    public void sendEmail(String to, String subject, String text, String name, String from) {
        try {
            // Cria e envia o e-mail
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText("Mensagem enviada por: " + name + " (" + from + ")\n\n" + text);
            mailSender.send(message);

            // Registra o e-mail enviado no banco de dados
            SentEmail sentEmail = new SentEmail(
                    from, // Remetente
                    to,
                    subject,
                    text,
                    name, // Nome do remetente
                    LocalDateTime.now());
            sentEmailRepository.save(sentEmail);

            System.out.println("E-mail enviado com sucesso para: " + to);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
    public List<SentEmail> getAllSentEmails() {
        return sentEmailRepository.findAll(); // Busca todos os e-mails salvos
    }
}