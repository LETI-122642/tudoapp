package iscteiul.ista.pdf;

import jakarta.persistence.*;

@Entity
public class PdfDocument {

    public static final int NAME_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }
}