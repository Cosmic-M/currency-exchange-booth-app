package developing.springboot.currencyexchangeboothapp.service.impl;

import developing.springboot.currencyexchangeboothapp.dto.ApiExchangeRateDto;
import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import developing.springboot.currencyexchangeboothapp.service.ExchangeRateService;
import developing.springboot.currencyexchangeboothapp.service.HttpClient;
import developing.springboot.currencyexchangeboothapp.service.mapper.ExchangeRateMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public List<ExchangeRate> syncExchangeRate() {
        ApiExchangeRateDto[] exchangeRateDto = httpClient.get(apiLink, ApiExchangeRateDto[].class);
        List<ExchangeRate> newestExchangeRateList = Arrays.stream(exchangeRateDto)
                .map(exchangeRateMapper::toModel)
                .toList();
        return updateLastExchangeRates(newestExchangeRateList);
    }

    public List<ExchangeRate> updateLastExchangeRates(List<ExchangeRate> newestExchangeRateList) {
        LocalDate today = LocalDate.now();
        List<ExchangeRate> listToSave = new ArrayList<>();
        for (ExchangeRate er : newestExchangeRateList) {
            ExchangeRate exchangeRateFromDb = exchangeRateRepository
                    .getByCcyAndBaseCcyAndDateTimeBetween(er.getCcy(), er.getBaseCcy(), today.atStartOfDay(), today.atTime(LocalTime.MAX));
            if (exchangeRateFromDb != null) {
                exchangeRateFromDb.setSale(er.getSale());
                exchangeRateFromDb.setBuy(er.getBuy());
                exchangeRateFromDb.setDateTime(er.getDateTime());
                listToSave.add(exchangeRateFromDb);
            } else {
                listToSave.add(er);
            }
        }
        return exchangeRateRepository.saveAll(listToSave);
    }
}
