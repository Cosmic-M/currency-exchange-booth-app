package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.dto.ApiCurrencyDto;
import developing.springboot.currencyexchangeboothapp.model.Currency;
import developing.springboot.currencyexchangeboothapp.repository.CurrencyRepository;
import developing.springboot.currencyexchangeboothapp.service.mapper.CurrencyMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;
    private final HttpClient httpClient;
    @Value(value = "${privateBankApiLink}")
    private String apiLink;

    public CurrencyServiceImpl(CurrencyMapper currencyMapper,
                               CurrencyRepository currencyRepository,
                               HttpClient httpClient) {
        this.currencyMapper = currencyMapper;
        this.currencyRepository = currencyRepository;
        this.httpClient = httpClient;
    }

    @Override
    public List<Currency> saveCurrentCurrencyQuotesToDB() {
        log.info("saveAll was invoked at " + LocalDateTime.now() + "link: "+ apiLink);
        ApiCurrencyDto[] currencyDto = httpClient.get(apiLink, ApiCurrencyDto[].class);
        List<Currency> currencyList = Arrays.stream(currencyDto)
                .map(currencyMapper::toModel)
                .toList();
        return currencyRepository.saveAll(currencyList);
    }
}
