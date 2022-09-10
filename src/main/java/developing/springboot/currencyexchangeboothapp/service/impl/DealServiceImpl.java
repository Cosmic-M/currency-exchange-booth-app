package developing.springboot.currencyexchangeboothapp.service.impl;

import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import developing.springboot.currencyexchangeboothapp.service.DealService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    @NonNull private ExchangeRateRepository exchangeRateRepository;
    @NonNull private DealRepository dealRepository;

    @Override
    public Deal create(Deal deal) {
        return dealRepository.save(fetchCcySale(deal));
    }

    private Deal fetchCcySale(Deal deal) {
        BigDecimal ccySale = exchangeRateRepository.getCcySale(deal.getCcySale());
        deal.setCcyBuyAmount(ccySale.multiply(deal.getCcySaleAmount()));
        return deal;
    }

    @Override
    public void deleteDealBy(String phone) {
        dealRepository.deleteBy(phone);
    }

    @Override
    public List<ReportResponse> doReport() {
        return dealRepository.getDealsPerDay();
    }

    @Override
    public List<Deal> findAllByCcyAndPeriod(String ccy,
                                            LocalDate from,
                                            LocalDate to,
                                            PageRequest pageRequest) {
        return dealRepository
                .findAllByCcyAndPeriod(ccy,
                        from.atStartOfDay(),
                        to.atTime(LocalTime.MAX),
                        pageRequest);
    }
}
