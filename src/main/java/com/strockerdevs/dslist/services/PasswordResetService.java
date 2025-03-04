package com.strockerdevs.dslist.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.strockerdevs.dslist.entities.PasswordResetToken;
import com.strockerdevs.dslist.entities.Pessoa;
import com.strockerdevs.dslist.repositories.PasswordResetTokenRepository;
import com.strockerdevs.dslist.repositories.PessoaRepository;

@Service
public class PasswordResetService {

    @Value("${app.token-expiration-hours}")
    private int tokenExpirationHours;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Injeta a URL base da aplicação
    @Value("${app.base-url}")
    private String appBaseUrl;

    public String createPasswordResetTokenForUser(String email) {
        // Busca o usuário pelo e-mail
        Pessoa user = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("O email fornecido não está cadastrado."));

        // Verifica se já existe um token ativo para o usuário
        Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenRepository.findActiveTokenByUser(user);

        if (existingTokenOpt.isPresent()) {
            throw new ActiveTokenExistsException("Você já possui um token ativo. Verifique seu e-mail.");
        }

        // Gera um novo token
        String token = UUID.randomUUID().toString();

        // Cria o token de redefinição de senha
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(tokenExpirationHours));
        passwordResetTokenRepository.save(passwordResetToken);

        // Envia o email com o token
        sendPasswordResetEmail(user.getEmail(), token);

        return token; // Retorna o token gerado
    }

    private void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Redefinição de Senha");
        message.setText("Você solicitou a redefinição de senha. Use o seguinte link para redefinir sua senha: "
                + appBaseUrl + "/reset-password?token=" + token); // Usa a URL base dinâmica
        mailSender.send(message);
    }

    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        // Atualiza a senha do usuário com criptografia
        Pessoa user = passwordResetToken.getUser();
        user.setSenha(passwordEncoder.encode(newPassword)); // Criptografa a senha
        pessoaRepository.save(user);

        // Remove o token após uso
        passwordResetTokenRepository.delete(passwordResetToken);

        return true;
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public class ActiveTokenExistsException extends RuntimeException {
        public ActiveTokenExistsException(String message) {
            super(message);
        }
    }
}