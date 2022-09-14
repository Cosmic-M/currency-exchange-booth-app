package developing.springboot.currencyexchangeboothapp.service.impl;

import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import developing.springboot.currencyexchangeboothapp.service.OtpPasswordService;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpPasswordServiceImpl implements OtpPasswordService {
    private final OtpPasswordRepository otpPasswordRepository;
    private final DealRepository dealRepository;

    @Override
    public Deal passwordValidation(String password, String phone) {
        Deal deal = dealRepository.findDealByPhoneAndStatus(phone, Status.NEW);
        if (deal == null) {
            throw new RuntimeException(
                    "Please, check if phone number from previous operation is the saim");
        }
        OtpPassword otpPassword = otpPasswordRepository.findByPassword(password);
        Status dealStatus = otpPassword != null ? Status.PERFORMED : Status.CANCELED;
        deal.setStatus(dealStatus);
        dealRepository.save(deal);
        Long dealId = deal.getId();
        otpPasswordRepository.deleteById(dealId);
        return deal;
    }

    @Override
    public OtpPassword create(Deal deal) {
        OtpPassword otpPassword = new OtpPassword();
        otpPassword.setId(deal.getId());
        otpPassword.setPassword(getPassword());
        otpPasswordRepository.save(otpPassword);
        return otpPassword;
    }

    private String getPassword() {
        Random random = new Random();
        int intValue = random.nextInt(999999);
        return String.format("%06d", intValue);
    }
}
