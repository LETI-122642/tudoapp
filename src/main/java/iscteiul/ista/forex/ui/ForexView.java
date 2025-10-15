// src/main/java/iscteiul/ista/forex/ui/ForexView.java
package iscteiul.ista.forex.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import iscteiul.ista.forex.ForexService;
import iscteiul.ista.forex.ForexExchange;
import iscteiul.ista.forex.ForexExchangeRepository;

@Route("forex")
@PageTitle("Intercâmbio de Moedas")
@Menu(order = 1, icon = "vaadin:exchange", title = "Intercâmbio de Moedas")
public class ForexView extends Main {
    private final ForexService forexService;
    private final ForexExchangeRepository repository;

    @Autowired
    public ForexView(ForexService forexService, ForexExchangeRepository repository) {
        this.forexService = forexService;
        this.repository = repository;

        TextField fromField = new TextField("De (ex: EUR)");
        TextField toField = new TextField("Para (ex: USD)");
        NumberField amountField = new NumberField("Valor");
        Button convertButton = new Button("Converter");

        Grid<ForexExchange> grid = new Grid<>(ForexExchange.class);
        grid.setItems(repository.findAll());

        convertButton.addClickListener(e -> {
            try {
                double result = forexService.convert(
                        fromField.getValue(),
                        toField.getValue(),
                        amountField.getValue()
                );
                Notification.show("Resultado: " + result + " " + toField.getValue());
                grid.setItems(repository.findAll());
            } catch (Exception ex) {
                Notification.show("Erro: " + ex.getMessage());
            }
        });

        FormLayout form = new FormLayout(fromField, toField, amountField, convertButton);
        VerticalLayout layout = new VerticalLayout(form, grid);
        add(layout);
    }
}
