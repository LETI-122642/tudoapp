package iscteiul.ista.forex;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ForexExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double result;
    private LocalDateTime exchangedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFromCurrency() { return fromCurrency; }
    public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }

    public String getToCurrency() { return toCurrency; }
    public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public LocalDateTime getExchangedAt() { return exchangedAt; }
    public void setExchangedAt(LocalDateTime exchangedAt) { this.exchangedAt = exchangedAt; }
}
