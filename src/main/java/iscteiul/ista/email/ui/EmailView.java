package iscteiul.ista.email.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import iscteiul.ista.email.EmailSent;
import iscteiul.ista.email.EmailService;
import org.apache.commons.mail.EmailException;

import java.util.Optional;

@Route("email")
@PageTitle("Email List")
@Menu(order = 2, icon = "vaadin:envelope", title = "Enviar Email")
public class EmailView extends Main {

    private final EmailService emailService;

    final TextField recipientField;
    final TextField subjectField;
    final TextArea bodyField;
    final Button sendButton;
    final Grid<EmailSent> emailGrid;

    public EmailView(EmailService emailService) {
        this.emailService = emailService;

        recipientField = new TextField();
        recipientField.setPlaceholder("Destinatário");
        recipientField.setWidth("20em");

        subjectField = new TextField();
        subjectField.setPlaceholder("Assunto");
        subjectField.setWidth("20em");

        bodyField = new TextArea();
        bodyField.setPlaceholder("Mensagem");
        bodyField.setWidth("25em");
        bodyField.setHeight("150px");

        sendButton = new Button("Enviar Email", e -> sendEmail());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        emailGrid = new Grid<>(EmailSent.class, false);
        emailGrid.addColumn(EmailSent::getRecipient).setHeader("Destinatário");
        emailGrid.addColumn(EmailSent::getSubject).setHeader("Assunto");
        emailGrid.addColumn(EmailSent::getSentAt).setHeader("Data de Envio");
        emailGrid.setItems(emailService.list());   // 🔹 igual ao PDF: .list()
        emailGrid.setSizeFull();

        setSizeFull();
        addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Gap.SMALL
        );

        add(recipientField, subjectField, bodyField, sendButton);
        add(emailGrid);
    }

    private void sendEmail() {
        String recipient = Optional.ofNullable(recipientField.getValue()).filter(s -> !s.isBlank()).orElse(null);
        String subject = Optional.ofNullable(subjectField.getValue()).orElse("(Sem assunto)");
        String body = Optional.ofNullable(bodyField.getValue()).orElse("");

        if (recipient == null) {
            Notification.show("O campo 'Destinatário' é obrigatório.", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        try {
            emailService.sendEmail(recipient, subject, body);
            emailGrid.setItems(emailService.list()); // recarrega a lista
            recipientField.clear();
            subjectField.clear();
            bodyField.clear();
            Notification.show("Email enviado com sucesso!", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (EmailException e) {
            Notification.show("Erro ao enviar email: " + e.getMessage(), 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
