package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.ApiCurrencyDto;
import developing.springboot.currencyexchangeboothapp.dto.CurrencyResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Currency;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyMapperTest {
    @InjectMocks
    private CurrencyMapper currencyMapper;
    private Currency currency;
    private ApiCurrencyDto apiCurrencyDto;

    @BeforeEach
    void setUp() {
        currency = new Currency();
        currency.setId(10L);
        currency.setCcy("EUR");
        currency.setBaseCcy("UAH");
        currency.setBuy(BigDecimal.valueOf(39.400000));
        currency.setSale(BigDecimal.valueOf(40.400000));
        currency.setDateTime(LocalDateTime
                .of(2022, 9, 6, 12, 0, 5, 0));

        CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto();
        currencyResponseDto.setId(10L);
        currencyResponseDto.setCcy("EUR");
        currencyResponseDto.setBaseCcy("UAH");
        currencyResponseDto.setBuy(BigDecimal.valueOf(39.400000));
        currencyResponseDto.setSale(BigDecimal.valueOf(40.400000));
        currencyResponseDto.setDateTime(LocalDateTime
                .of(2022, 9, 6, 12, 0, 5, 0));

        apiCurrencyDto = new ApiCurrencyDto();
        apiCurrencyDto.setCcy("EUR");
        apiCurrencyDto.setBase_ccy("UAH");
        apiCurrencyDto.setBuy(BigDecimal.valueOf(39.400000));
        apiCurrencyDto.setSale(BigDecimal.valueOf(40.400000));
    }

    @Test
    void mapToModel_ok() {
        Currency result = currencyMapper.toModel(apiCurrencyDto);
        Assertions.assertEquals("EUR", result.getCcy());
        Assertions.assertEquals("UAH", result.getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(39.400000), result.getBuy());
        Assertions.assertEquals(BigDecimal.valueOf(40.400000), result.getSale());
    }

    @Test
    void mapToDto_ok() {
        CurrencyResponseDto result = currencyMapper.toDto(currency);
        Assertions.assertEquals(10L, currency.getId());
        Assertions.assertEquals("EUR", result.getCcy());
        Assertions.assertEquals("UAH", result.getBaseCcy());
        Assertions.assertEquals(BigDecimal.valueOf(39.400000), result.getBuy());
        Assertions.assertEquals(BigDecimal.valueOf(40.400000), result.getSale());
        Assertions.assertEquals(LocalDateTime
                .of(2022, 9, 6, 12, 0, 5, 0),
                result.getDateTime());
    }
}
