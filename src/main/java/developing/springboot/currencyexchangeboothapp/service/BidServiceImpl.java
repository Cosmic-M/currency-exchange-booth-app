package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Bid;
import developing.springboot.currencyexchangeboothapp.model.BidIdPassword;
import developing.springboot.currencyexchangeboothapp.repository.BidIdPasswordRepository;
import developing.springboot.currencyexchangeboothapp.repository.BidRepository;
import developing.springboot.currencyexchangeboothapp.repository.CurrencyRepository;
import developing.springboot.currencyexchangeboothapp.service.mapper.BidMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidServiceImpl implements BidService {
    @NonNull CurrencyRepository currencyRepository;
    @NonNull BidRepository bidRepository;
    @NonNull BidIdPasswordRepository bidIdPasswordRepository;

    @Override
    public Bid createBid(Bid bid) {
        BigDecimal ccySale = currencyRepository.getCcySale(bid.getCcySale());
        bid.setCcyBuyAmount(ccySale.multiply(bid.getCcySaleAmount()));
        bid = bidRepository.save(bid);
        BidIdPassword bidIdPassword = new BidIdPassword();
        bidIdPassword.setId(bid.getId());
        bidIdPassword.setPassword(getPassword());
        bidIdPasswordRepository.save(bidIdPassword);
        return bid;
    }

    private String getPassword() {
        Random random = new Random();
        int intValue = random.nextInt(999999);
        return String.format("%06d", intValue);
    }
}
