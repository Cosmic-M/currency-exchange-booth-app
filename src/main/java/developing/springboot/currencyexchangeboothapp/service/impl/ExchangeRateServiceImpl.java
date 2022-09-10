package developing.springboot.currencyexchangeboothapp.service.impl;

import developing.springboot.currencyexchangeboothapp.dto.ApiExchangeRateDto;
import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import developing.springboot.currencyexchangeboothapp.service.ExchangeRateService;
import developing.springboot.currencyexchangeboothapp.service.HttpClient;
import developing.springboot.currencyexchangeboothapp.service.mapper.ExchangeRateMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateMapper exchangeRateMapper;
    private final ExchangeRateRepository exchangeRateRepository;
    private final HttpClient httpClient;
    @Value(value = "${privateBankApiLink}")
    private String apiLink;

    public ExchangeRateServiceImpl(ExchangeRateMapper exchangeRateMapper,
                                   ExchangeRateRepository exchangeRateRepository,
                               HttpClient httpClient) {
        this.exchangeRateMapper = exchangeRateMapper;
        this.exchangeRateRepository = exchangeRateRepository;
        this.httpClient = httpClient;
    }

    @Override
    public List<ExchangeRate> getExchangeRate() {
        log.info("saveAll was invoked at " + LocalDateTime.now() + "link: " + apiLink);
        ApiExchangeRateDto[] exchangeRateDto = httpClient.get(apiLink, ApiExchangeRateDto[].class);
        return Arrays.stream(exchangeRateDto)
                .map(exchangeRateMapper::toModel)
                .toList();
    }

    @Override
    public List<ExchangeRate> save(List<ExchangeRate> exchangeRateList) {
        return exchangeRateRepository.saveAll(exchangeRateList);
    }
}
