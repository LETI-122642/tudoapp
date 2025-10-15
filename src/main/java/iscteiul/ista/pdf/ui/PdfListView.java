package iscteiul.ista.pdf.ui;

import iscteiul.ista.pdf.PdfDocument;
import iscteiul.ista.pdf.PdfService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.html.Anchor;

import java.io.IOException;
import java.util.Optional;

@Route("pdf")
@PageTitle("PDF List")
@Menu(order = 1, icon = "vaadin:file-text", title = "PDF List")
class PdfListView extends Main {

    private final PdfService pdfService;

    final TextField nameField;
    final TextField textField;
    final Button createBtn;
    final Grid<PdfDocument> pdfGrid;

    PdfListView(PdfService pdfService) {
        this.pdfService = pdfService;

        nameField = new TextField();
        nameField.setPlaceholder("PDF name");
        nameField.setAriaLabel("PDF name");
        nameField.setMaxLength(PdfDocument.NAME_MAX_LENGTH);
        nameField.setMinWidth("20em");

        textField = new TextField();
        textField.setPlaceholder("Text for PDF");
        textField.setAriaLabel("PDF text");
        textField.setMinWidth("20em");

        createBtn = new Button("Create PDF", event -> createPdf());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        pdfGrid = new Grid<>();
        pdfGrid.setItems(pdfService.list());
        pdfGrid.addColumn(PdfDocument::getName).setHeader("Name");
        pdfGrid.addComponentColumn(pdf -> {
            Anchor download = new Anchor("/api/pdf/" + pdf.getId(), "Download");
            download.getElement().setAttribute("download", true);
            return download;
        }).setHeader("Download");
        pdfGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(nameField, textField, createBtn);
        add(pdfGrid);
    }

    private void createPdf() {
        String name = Optional.ofNullable(nameField.getValue()).filter(s -> !s.isBlank()).orElse("document.pdf");
        String text = textField.getValue();
        try {
            pdfService.createPdf(text, name);
            pdfGrid.setItems(pdfService.list());
            nameField.clear();
            textField.clear();
            Notification.show("PDF added", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (IOException e) {
            Notification.show("Error generating PDF", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}