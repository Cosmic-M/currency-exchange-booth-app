package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    @NonNull private ExchangeRateRepository exchangeRateRepository;
    @NonNull private DealRepository dealRepository;
    @NonNull private OtpPasswordRepository otpPasswordRepository;

    @Override
    public Deal create(Deal deal) {
        BigDecimal ccySale = exchangeRateRepository.getCcySale(deal.getCcySale());
        deal.setCcyBuyAmount(ccySale.multiply(deal.getCcySaleAmount()));
        deal = dealRepository.save(deal);
        OtpPassword bidIdPassword = new OtpPassword();
        bidIdPassword.setId(deal.getId());
        bidIdPassword.setPassword(getPassword());
        otpPasswordRepository.save(bidIdPassword);
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
    public List<Deal> findAllByCcyAndPeriod(String ccy, LocalDate from, LocalDate to) {
        return dealRepository
                .findAllByCcyAndPeriod(ccy, from.atStartOfDay(), to.atTime(LocalTime.MAX));
    }

    private String getPassword() {
        Random random = new Random();
        int intValue = random.nextInt(999999);
        return String.format("%06d", intValue);
    }
}
