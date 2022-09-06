package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.ApiCurrencyDto;
import developing.springboot.currencyexchangeboothapp.dto.CurrencyResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Currency;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public Currency toModel(ApiCurrencyDto currencyDto) {
        Currency currency = new Currency();
        currency.setCcy(currencyDto.getCcy());
        currency.setBaseCcy(currencyDto.getBase_ccy());
        currency.setSale(currencyDto.getSale());
        currency.setBuy(currencyDto.getBuy());
        currency.setDateTime(LocalDateTime.now());
        return currency;
    }

    public CurrencyResponseDto toDto(Currency currency) {
        CurrencyResponseDto responseDto = new CurrencyResponseDto();
        responseDto.setId(currency.getId());
        responseDto.setCcy(currency.getCcy());
        responseDto.setBaseCcy(currency.getBaseCcy());
        responseDto.setSale(currency.getSale());
        responseDto.setBuy(currency.getBuy());
        responseDto.setDateTime(currency.getDateTime());
        return responseDto;
    }
}
