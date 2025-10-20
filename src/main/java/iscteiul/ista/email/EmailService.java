package iscteiul.ista.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final EmailSentRepository repository;

    @Value("${mail.host:smtp.gmail.com}")
    String mailHost;

    @Value("${mail.username:}")
    String username;

    @Value("${mail.password:}")
    String password;

    public EmailService(EmailSentRepository repository) {
        this.repository = repository;
    }

    /** Igual ao PdfService: devolve a lista para o Grid. */
    public List<EmailSent> list() {
        return repository.findAll(); // se quiseres por ordem, usa Sort no repo
    }

    public void sendEmail(String to, String subject, String message) throws EmailException {
        // Envio real (se tiveres credenciais). Se não tiveres, comenta este bloco.
        if (!username.isBlank()) {
            SimpleEmail email = new SimpleEmail();
            email.setHostName(mailHost);
            email.setSmtpPort(587);
            email.setAuthentication(username, password);
            email.setStartTLSEnabled(true);
            email.setFrom(username);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);
            email.send();
        }
        // Regista sempre o envio (real ou simulado)
        repository.save(new EmailSent(to, subject, message));
    }
}
