package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.repository.CurrencyRepository;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import java.math.BigDecimal;
import java.util.Random;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    @NonNull private CurrencyRepository currencyRepository;
    @NonNull private DealRepository dealRepository;
    @NonNull private OtpPasswordRepository otpPasswordRepository;

    @Override
    public Deal create(Deal deal) {
        BigDecimal ccySale = currencyRepository.getCcySale(deal.getCcySale());
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

    private String getPassword() {
        Random random = new Random();
        int intValue = random.nextInt(999999);
        return String.format("%06d", intValue);
    }
}
