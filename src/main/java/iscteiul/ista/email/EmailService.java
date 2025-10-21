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

    public List<EmailSent> list() {
        return repository.findAll(); 
    }

    public void sendEmail(String to, String subject, String message) throws EmailException {
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
        repository.save(new EmailSent(to, subject, message));
    }
}
