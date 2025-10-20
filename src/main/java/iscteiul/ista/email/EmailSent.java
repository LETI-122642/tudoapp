package iscteiul.ista.email;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EmailSent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String subject;

    @Lob
    private String body;

    private LocalDateTime sentAt;

    protected EmailSent() { }

    public EmailSent(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.sentAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public LocalDateTime getSentAt() { return sentAt; }
}
