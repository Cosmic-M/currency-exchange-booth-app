package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "developing.springboot.currencyexchangeboothapp")
class ExchangeRateRepositoryTest {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    private ExchangeRate exchangeRate;

    @BeforeEach
    void setUp() {
        exchangeRate = new ExchangeRate();
        exchangeRate.setCcy("EUR");
        exchangeRate.setBaseCcy("UAH");
        exchangeRate.setBuy(BigDecimal.valueOf(39.400000));
        exchangeRate.setSale(BigDecimal.valueOf(40.400000));
        exchangeRate.setDateTime(LocalDateTime.now());
    }

    @Test
    void expectToGetActualCcySale_ok() {
        exchangeRateRepository.save(exchangeRate);
        BigDecimal ccySale = exchangeRateRepository.getCcySale(exchangeRate.getCcy());
        Assertions.assertNotNull(ccySale);
        Assertions.assertEquals(BigDecimal.valueOf(404000, 4), ccySale);
    }

    @Test
    void testingWithOutOfDateData_notOk() {
        exchangeRate.setDateTime(LocalDateTime.of(1991, 8, 24, 12,0,0,0));
        exchangeRateRepository.save(exchangeRate);
        BigDecimal ccySaleFromDb = exchangeRateRepository.getCcySale(exchangeRate.getCcy());
        Assertions.assertNull(ccySaleFromDb);
    }
}
