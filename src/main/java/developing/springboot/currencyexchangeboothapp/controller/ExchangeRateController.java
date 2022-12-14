package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.response.ExchangeRateResponseDto;
import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import developing.springboot.currencyexchangeboothapp.service.ExchangeRateService;
import developing.springboot.currencyexchangeboothapp.service.mapper.ExchangeRateMapper;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateMapper exchangeRateMapper;

    @ApiOperation(value = "method obtains current exchange rate")
    @GetMapping("/open-work-day")
    public List<ExchangeRateResponseDto> openWorkDay() {
        List<ExchangeRate> exchangeRateList = exchangeRateService.syncExchangeRate();
        return exchangeRateList.stream().map(exchangeRateMapper::toDto).toList();
    }
}
