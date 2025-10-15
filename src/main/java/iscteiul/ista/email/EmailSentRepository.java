package iscteiul.ista.email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSentRepository extends JpaRepository<EmailSent, Long> {
}
