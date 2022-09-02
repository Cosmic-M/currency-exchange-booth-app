package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.CurrencyResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Currency;
import developing.springboot.currencyexchangeboothapp.service.CurrencyService;
import developing.springboot.currencyexchangeboothapp.service.mapper.CurrencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    @GetMapping("/current")
    public List<CurrencyResponseDto> openWorkDay() {
        List<Currency> currencyList = currencyService.saveCurrentCurrencyQuotesToDB();
        return currencyList.stream().map(currencyMapper::toDto).toList();
    }
}
