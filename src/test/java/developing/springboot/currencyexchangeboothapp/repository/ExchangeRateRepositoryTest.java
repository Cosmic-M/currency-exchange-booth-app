package developing.springboot.currencyexchangeboothapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
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
        exchangeRate.setBuy(BigDecimal.valueOf(39.40000));
        exchangeRate.setSale(BigDecimal.valueOf(40.40000));
        exchangeRate.setDateTime(LocalDateTime.now());
    }

    @AfterEach
    void clearRepo() {
        exchangeRateRepository.deleteAll();
    }

    @Test
    void getCcySaleForCurrency_dataIsActual_ok() {
        exchangeRateRepository.save(exchangeRate);
        BigDecimal ccySale = exchangeRateRepository.getCcySaleForCurrency(exchangeRate.getCcy());
        Assertions.assertNotNull(ccySale);
        assertThat(ccySale).isEqualByComparingTo(BigDecimal.valueOf(40.40));
    }

    @Test
    void getCcySaleForCurrency_outOfDateData_notOk() {
        exchangeRate.setDateTime(LocalDateTime.of(1991, 8, 24, 12,0,0,0));
        exchangeRateRepository.save(exchangeRate);
        BigDecimal ccySaleFromDb = exchangeRateRepository
                .getCcySaleForCurrency(exchangeRate.getCcy());
        Assertions.assertNull(ccySaleFromDb);
    }

    @Test
    void getCcySaleForNationCurrency_dataIsActual_ok() {
        exchangeRateRepository.save(exchangeRate);
        BigDecimal ccySale = exchangeRateRepository
                .getCcySaleForNationCurrency(exchangeRate.getCcy());
        Assertions.assertNotNull(ccySale);
        assertThat(ccySale).isEqualByComparingTo(BigDecimal.valueOf(39.40));
    }

    @Test
    void getCcySaleForNationCurrency_outOfDateData_notOk() {
        exchangeRate.setDateTime(LocalDateTime.of(1991, 8, 24, 12,0,0,0));
        exchangeRateRepository.save(exchangeRate);
        BigDecimal ccySaleFromDb = exchangeRateRepository
                .getCcySaleForCurrency(exchangeRate.getBaseCcy());
        Assertions.assertNull(ccySaleFromDb);
    }
}
