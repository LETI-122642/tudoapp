package iscteiul.ista.forex;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.MonetaryConversions;
import java.time.LocalDateTime;

@Service
public class ForexService {
    @Autowired
    private ForexExchangeRepository repository;

    public double convert(String from, String to, double amount) {
        CurrencyUnit fromCur = Monetary.getCurrency(from);
        CurrencyUnit toCur = Monetary.getCurrency(to);

        Money money = Money.of(amount, fromCur);
        var provider = MonetaryConversions.getExchangeRateProvider("ECB");
        var conversion = provider.getCurrencyConversion(toCur);
        Money result = money.with(conversion);

        ForexExchange exchange = new ForexExchange();
        exchange.setFromCurrency(from);
        exchange.setToCurrency(to);
        exchange.setAmount(amount);
        exchange.setResult(result.getNumber().doubleValue());
        exchange.setExchangedAt(LocalDateTime.now());
        repository.save(exchange);

        return result.getNumber().doubleValue();
    }
}
