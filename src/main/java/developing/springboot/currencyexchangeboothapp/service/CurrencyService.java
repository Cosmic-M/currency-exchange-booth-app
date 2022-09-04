package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Currency;
import java.util.List;

public interface CurrencyService {
    List<Currency> saveCurrentCurrencyQuotesToDB();
}
