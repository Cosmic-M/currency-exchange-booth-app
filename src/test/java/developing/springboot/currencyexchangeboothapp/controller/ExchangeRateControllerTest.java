package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import developing.springboot.currencyexchangeboothapp.service.ExchangeRateService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateControllerTest {
    @MockBean
    private ExchangeRateService exchangeRateService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void openWorkDay() {
        ExchangeRate usd = new ExchangeRate();
        usd.setId(1L);
        usd.setCcy("USD");
        usd.setBaseCcy("UAH");
        usd.setBuy(new BigDecimal("39.400000"));
        usd.setSale(new BigDecimal("39.900000"));
        usd.setDateTime(LocalDateTime.now());

        ExchangeRate eur = new ExchangeRate();
        eur.setId(2L);
        eur.setCcy("EUR");
        eur.setBaseCcy("UAH");
        eur.setBuy(new BigDecimal("39.500000"));
        eur.setSale(new BigDecimal("40.400000"));
        eur.setDateTime(LocalDateTime.now());

        List<ExchangeRate> mockExchangeRateList = List.of(usd, eur);

        Mockito.when(exchangeRateService.getExchangeRate())
                .thenReturn(mockExchangeRateList);

        Mockito.when(exchangeRateService.save(mockExchangeRateList))
                .thenReturn(mockExchangeRateList);

        RestAssuredMockMvc.when()
                .get("/exchange-rate/open-work-day")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].ccy", Matchers.equalTo("USD"))
                .body("[0].baseCcy", Matchers.equalTo("UAH"))
//                .body("[0].buy", Matchers.equalTo(BigDecimal.valueOf(39.400000)))
//                .body("[0].sale", Matchers.equalTo(BigDecimal.valueOf(39.900000)))
                .body("[0].buy", Matchers.equalTo(39.4F))
                .body("[0].sale", Matchers.equalTo(39.9F))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].ccy", Matchers.equalTo("EUR"))
                .body("[1].baseCcy", Matchers.equalTo("UAH"))
//                .body("[1].buy", Matchers.equalTo(BigDecimal.valueOf(39.500000)))
//                .body("[1].sale", Matchers.equalTo(BigDecimal.valueOf(40.400000)));
                .body("[1].buy", Matchers.equalTo(39.5F))
                .body("[1].sale", Matchers.equalTo(40.4F));
    }
}