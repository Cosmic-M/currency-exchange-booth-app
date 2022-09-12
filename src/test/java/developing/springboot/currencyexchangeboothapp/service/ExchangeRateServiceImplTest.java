package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import developing.springboot.currencyexchangeboothapp.service.impl.ExchangeRateServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "developing.springboot.currencyexchangeboothapp")
class ExchangeRateServiceImplTest {
    @Autowired
    private ExchangeRateServiceImpl exchangeRateService;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    private List<ExchangeRate> todayExchangeRateList;
    private List<ExchangeRate> newestExchangeRateList;

    @BeforeEach
    void setUp() {
        ExchangeRate todayExchangeRateUsd = new ExchangeRate();
        todayExchangeRateUsd.setId(1L);
        todayExchangeRateUsd.setCcy("USD");
        todayExchangeRateUsd.setBaseCcy("UAH");
        todayExchangeRateUsd.setBuy(BigDecimal.valueOf(39.400000));
        todayExchangeRateUsd.setSale(BigDecimal.valueOf(39.900000));
        todayExchangeRateUsd.setDateTime(LocalDate.now().atStartOfDay());

        ExchangeRate todayExchangeRateEur = new ExchangeRate();
        todayExchangeRateEur.setId(2L);
        todayExchangeRateEur.setCcy("EUR");
        todayExchangeRateEur.setBaseCcy("UAH");
        todayExchangeRateEur.setBuy(BigDecimal.valueOf(39.400000));
        todayExchangeRateEur.setSale(BigDecimal.valueOf(40.400000));
        todayExchangeRateEur.setDateTime(LocalDate.now().atStartOfDay());
        todayExchangeRateList = new ArrayList<>();
        todayExchangeRateList.add(todayExchangeRateUsd);
        todayExchangeRateList.add(todayExchangeRateEur);

        ExchangeRate newestExchangeRateUsd = new ExchangeRate();
        newestExchangeRateUsd.setId(1L);
        newestExchangeRateUsd.setCcy("USD");
        newestExchangeRateUsd.setBaseCcy("UAH");
        newestExchangeRateUsd.setBuy(BigDecimal.valueOf(12.250000));
        newestExchangeRateUsd.setSale(BigDecimal.valueOf(12.500000));
        newestExchangeRateUsd.setDateTime(LocalDateTime.now());

        ExchangeRate newestExchangeRateEur = new ExchangeRate();
        newestExchangeRateEur.setId(2L);
        newestExchangeRateEur.setCcy("EUR");
        newestExchangeRateEur.setBaseCcy("UAH");
        newestExchangeRateEur.setBuy(BigDecimal.valueOf(14.500000));
        newestExchangeRateEur.setSale(BigDecimal.valueOf(14.900000));
        newestExchangeRateEur.setDateTime(LocalDateTime.now());
        newestExchangeRateList = new ArrayList<>();
        newestExchangeRateList.add(newestExchangeRateUsd);
        newestExchangeRateList.add(newestExchangeRateEur);
    }

    @Test
    void syncExchangeRate_useValidLink_ok() {
        List<ExchangeRate> result = exchangeRateService.syncExchangeRate();
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

    @Test
    void updateLastExchangeRates_saveNewData_ok() {
        LocalDate today = LocalDate.now();
        Mockito.when(exchangeRateRepository
                .getByCcyAndBaseCcyAndDateTimeBetween("USD", "UAH", today.atStartOfDay(),
                        today.atTime(LocalTime.MAX))).thenReturn(null);
        Mockito.when(exchangeRateRepository
                .getByCcyAndBaseCcyAndDateTimeBetween("EUR", "UAH", today.atStartOfDay(),
                        today.atTime(LocalTime.MAX))).thenReturn(null);
        Mockito.when(exchangeRateRepository
                .saveAll(newestExchangeRateList)).thenReturn(newestExchangeRateList);

        Assertions.assertEquals("USD", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getCcy());
        Assertions.assertEquals("UAH", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(12.50), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getSale());
        Assertions.assertEquals(BigDecimal.valueOf(12.25), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getBuy());

        Assertions.assertEquals("EUR", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getCcy());
        Assertions.assertEquals("UAH", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(14.9), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getSale());
        Assertions.assertEquals(BigDecimal.valueOf(14.5), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getBuy());
    }

    @Test
    void updateLastExchangeRates_updateOutOfDateData_ok() {
        LocalDate today = LocalDate.now();
        Mockito.when(exchangeRateRepository
                .getByCcyAndBaseCcyAndDateTimeBetween("USD", "UAH", today.atStartOfDay(),
                        today.atTime(LocalTime.MAX))).thenReturn(todayExchangeRateList.get(0));
        Mockito.when(exchangeRateRepository
                .getByCcyAndBaseCcyAndDateTimeBetween("EUR", "UAH", today.atStartOfDay(),
                        today.atTime(LocalTime.MAX))).thenReturn(todayExchangeRateList.get(1));
        Mockito.when(exchangeRateRepository
                .saveAll(newestExchangeRateList)).thenReturn(newestExchangeRateList);

        Assertions.assertEquals("USD", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getCcy());
        Assertions.assertEquals("UAH", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(12.50), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getSale());
        Assertions.assertEquals(BigDecimal.valueOf(12.25), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(0).getBuy());

        Assertions.assertEquals("EUR", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getCcy());
        Assertions.assertEquals("UAH", exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(14.9), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getSale());
        Assertions.assertEquals(BigDecimal.valueOf(14.5), exchangeRateService
                .updateLastExchangeRates(newestExchangeRateList).get(1).getBuy());
    }
}
