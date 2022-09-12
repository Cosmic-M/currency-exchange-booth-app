package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.ApiExchangeRateDto;
import developing.springboot.currencyexchangeboothapp.dto.ExchangeRateResponseDto;
import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapper {
    public ExchangeRate toModel(ApiExchangeRateDto apiExchangeRateDto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCcy(apiExchangeRateDto.getCcy());
        exchangeRate.setBaseCcy(apiExchangeRateDto.getBaseCcy());
        exchangeRate.setSale(apiExchangeRateDto.getSale());
        exchangeRate.setBuy(apiExchangeRateDto.getBuy());
        exchangeRate.setDateTime(LocalDateTime.now());
        return exchangeRate;
    }

    public ExchangeRateResponseDto toDto(ExchangeRate exchangeRate) {
        ExchangeRateResponseDto responseDto = new ExchangeRateResponseDto();
        responseDto.setId(exchangeRate.getId());
        responseDto.setCcy(exchangeRate.getCcy());
        responseDto.setBaseCcy(exchangeRate.getBaseCcy());
        responseDto.setSale(exchangeRate.getSale());
        responseDto.setBuy(exchangeRate.getBuy());
        responseDto.setDateTime(exchangeRate.getDateTime());
        return responseDto;
    }
}
