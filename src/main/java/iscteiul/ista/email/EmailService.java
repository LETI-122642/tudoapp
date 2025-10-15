package iscteiul.ista.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    @Autowired
    private EmailSentRepository repository;

    public void sendEmail(String to, String subject, String body) throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.seuprovedor.com");
        email.setSmtpPort(587);
        email.setAuthentication("usuario", "senha");
        email.setStartTLSEnabled(true);
        email.setFrom("seu@email.com");
        email.setSubject(subject);
        email.setMsg(body);
        email.addTo(to);
        email.send();

        EmailSent sent = new EmailSent();
        sent.setToAddress(to);
        sent.setSubject(subject);
        sent.setBody(body);
        sent.setSentAt(LocalDateTime.now());
        repository.save(sent);
    }
}
