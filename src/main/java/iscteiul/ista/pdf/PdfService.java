package iscteiul.ista.pdf;

    import org.apache.pdfbox.pdmodel.PDDocument;
    import org.apache.pdfbox.pdmodel.PDPage;
    import org.apache.pdfbox.pdmodel.PDPageContentStream;
    import org.apache.pdfbox.pdmodel.font.PDType1Font;
    import org.springframework.stereotype.Service;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.util.List;

    @Service
    public class PdfService {

        private final PdfDocumentRepository repository;

        public PdfService(PdfDocumentRepository repository) {
            this.repository = repository;
        }

        public PdfDocument createPdf(String text, String name) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (PDDocument doc = new PDDocument()) {
                PDPage page = new PDPage();
                doc.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(text);
                contentStream.endText();
                contentStream.close();
                doc.save(out);
            }
            PdfDocument pdf = new PdfDocument();
            pdf.setName(name);
            pdf.setContent(out.toByteArray());
            return repository.save(pdf);
        }

        public List<PdfDocument> list() {
            return repository.findAll();
        }

        public PdfDocument getPdf(Long id) {
            return repository.findById(id).orElse(null);
        }
    }