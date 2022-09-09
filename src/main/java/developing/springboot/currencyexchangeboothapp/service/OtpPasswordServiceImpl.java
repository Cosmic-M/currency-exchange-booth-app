package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpPasswordServiceImpl implements OtpPasswordService {
    @NonNull private OtpPasswordRepository otpPasswordRepository;
    @NonNull private DealRepository dealRepository;

    @Override
    public Deal passwordValidation(OtpPassword otpPassword) {
        boolean isPresent = otpPasswordRepository.exists(Example.of(otpPassword));
        Deal deal = dealRepository.findCoincidenceById(otpPassword.getId());
        otpPasswordRepository.delete(otpPassword);
        Status status = isPresent ? Status.PERFORMED : Status.CANCELED;
        deal.setStatus(status);
        dealRepository.save(deal);
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
