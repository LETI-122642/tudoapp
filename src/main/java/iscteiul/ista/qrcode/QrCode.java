package iscteiul.ista.qrcode;

import java.time.LocalDateTime;
import java.util.UUID;

public class QrCode {
    private UUID id;
    private String content;
    private LocalDateTime createdAt;

    public QrCode(String content) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
