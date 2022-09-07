package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRate> getExchangeRate();

    List<ExchangeRate> save(List<ExchangeRate> exchangeRateList);
}
