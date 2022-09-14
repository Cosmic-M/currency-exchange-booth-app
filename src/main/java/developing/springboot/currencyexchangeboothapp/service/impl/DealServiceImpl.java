package developing.springboot.currencyexchangeboothapp.service.impl;

import developing.springboot.currencyexchangeboothapp.dto.response.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import developing.springboot.currencyexchangeboothapp.service.DealService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final DealRepository dealRepository;
    private final OtpPasswordRepository otpPasswordRepository;

    @Override
    public Deal create(Deal deal) {
        return dealRepository.save(fetchCcySale(deal));
    }

    private Deal fetchCcySale(Deal deal) {
        String ccy = deal.getCcySale();
        BigDecimal ccyCurrencyRate = ccy.equals("UAH")
                ? exchangeRateRepository.getCcySaleForNationCurrency(deal.getCcyBuy())
                : exchangeRateRepository.getCcySaleForCurrency(ccy);
        deal.setCcyBuyAmount(ccyCurrencyRate.multiply(deal.getCcySaleAmount()));
        return deal;
    }

    @Override
    public void deleteDealBy(String phone) {
        Deal dealDeletedFromDb = dealRepository.findDealByPhoneAndStatus(phone, Status.NEW);
        dealRepository.delete(dealDeletedFromDb);
        otpPasswordRepository.deleteById(dealDeletedFromDb.getId());
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
