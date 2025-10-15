package iscteiul.ista.email.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Main;
import org.springframework.beans.factory.annotation.Autowired;
import iscteiul.ista.email.EmailService;
import iscteiul.ista.email.EmailSent;
import iscteiul.ista.email.EmailSentRepository;

@PageTitle("Send Email")
@Menu(order = 1, icon = "vaadin:clipboard-check", title = "Send Email")
@Route("email")
public class EmailView extends Main {
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailSentRepository repository;

    public EmailView() {
        TextField toField = new TextField("Para");
        TextField subjectField = new TextField("Assunto");
        TextArea bodyField = new TextArea("Mensagem");
        Button sendButton = new Button("Enviar");

        Grid<EmailSent> grid = new Grid<>(EmailSent.class);
        grid.setItems(repository.findAll());

        sendButton.addClickListener(e -> {
            try {
                emailService.sendEmail(toField.getValue(), subjectField.getValue(), bodyField.getValue());
                Notification.show("E-mail enviado!");
                grid.setItems(repository.findAll());
            } catch (Exception ex) {
                Notification.show("Erro ao enviar: " + ex.getMessage());
            }
        });

        FormLayout form = new FormLayout(toField, subjectField, bodyField, sendButton);
        VerticalLayout layout = new VerticalLayout(form, grid);
        add(layout);
    }
}
