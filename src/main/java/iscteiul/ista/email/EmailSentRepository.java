package iscteiul.ista.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSentRepository extends JpaRepository<EmailSent, Long> { }
