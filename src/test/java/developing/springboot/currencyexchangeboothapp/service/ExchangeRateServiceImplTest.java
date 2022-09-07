package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "developing.springboot.currencyexchangeboothapp")
class ExchangeRateServiceImplTest {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    void getActualExchangeRate_useValidLink_ok() {
        List<ExchangeRate> result = exchangeRateService.getExchangeRate();
        List<String> values = List.of("EUR", "USD");
        Assertions.assertNotNull(result.get(0).getCcy());
        Assertions.assertNotNull(result.get(0).getBaseCcy());
        Assertions.assertNotNull(result.get(0).getSale());
        Assertions.assertNotNull(result.get(0).getBuy());
        List<String> currencyList = result.stream()
                .map(ExchangeRate::getCcy)
                .toList();
        Assertions.assertTrue(currencyList.containsAll(values));
    }
}
