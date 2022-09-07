package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.ApiExchangeRateDto;
import developing.springboot.currencyexchangeboothapp.dto.ExchangeRateResponseDto;
import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExchangeRateMapperTest {
    @InjectMocks
    private ExchangeRateMapper exchangeRateMapper;
    private ExchangeRate exchangeRate;
    private ApiExchangeRateDto apiExchangeRateDto;

    @BeforeEach
    void setUp() {
        exchangeRate = new ExchangeRate();
        exchangeRate.setId(10L);
        exchangeRate.setCcy("EUR");
        exchangeRate.setBaseCcy("UAH");
        exchangeRate.setBuy(BigDecimal.valueOf(39.400000));
        exchangeRate.setSale(BigDecimal.valueOf(40.400000));
        exchangeRate.setDateTime(LocalDateTime
                .of(2022, 9, 6, 12, 0, 5, 0));

        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();
        exchangeRateResponseDto.setId(10L);
        exchangeRateResponseDto.setCcy("EUR");
        exchangeRateResponseDto.setBaseCcy("UAH");
        exchangeRateResponseDto.setBuy(BigDecimal.valueOf(39.400000));
        exchangeRateResponseDto.setSale(BigDecimal.valueOf(40.400000));
        exchangeRateResponseDto.setDateTime(LocalDateTime
                .of(2022, 9, 6, 12, 0, 5, 0));

        apiExchangeRateDto = new ApiExchangeRateDto();
        apiExchangeRateDto.setCcy("EUR");
        apiExchangeRateDto.setBase_ccy("UAH");
        apiExchangeRateDto.setBuy(BigDecimal.valueOf(39.400000));
        apiExchangeRateDto.setSale(BigDecimal.valueOf(40.400000));
    }

    @Test
    void mapToModel_ok() {
        ExchangeRate result = exchangeRateMapper.toModel(apiExchangeRateDto);
        Assertions.assertEquals("EUR", result.getCcy());
        Assertions.assertEquals("UAH", result.getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(39.400000), result.getBuy());
        Assertions.assertEquals(BigDecimal.valueOf(40.400000), result.getSale());
    }

    @Test
    void mapToDto_ok() {
        ExchangeRateResponseDto result = exchangeRateMapper.toDto(exchangeRate);
        Assertions.assertEquals(10L, exchangeRate.getId());
        Assertions.assertEquals("EUR", result.getCcy());
        Assertions.assertEquals("UAH", result.getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(39.400000), result.getBuy());
        Assertions.assertEquals(BigDecimal.valueOf(40.400000), result.getSale());
        Assertions.assertEquals(LocalDateTime
                .of(2022, 9, 6, 12, 0, 5, 0),
                result.getDateTime());
    }
}
