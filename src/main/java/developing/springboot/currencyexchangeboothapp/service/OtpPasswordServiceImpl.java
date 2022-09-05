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

@Service
@RequiredArgsConstructor
public class OtpPasswordServiceImpl implements OtpPasswordService {
    @NonNull private OtpPasswordRepository bidIdPasswordRepository;
    @NonNull private DealRepository bidRepository;

    @Override
    public Deal passwordValidation(OtpPassword bidIdPassword) {
        boolean isPresent = bidIdPasswordRepository.exists(Example.of(bidIdPassword));
        Deal bid = bidRepository.findCoincidenceById(bidIdPassword.getId());
        bidIdPasswordRepository.delete(bidIdPassword);
        Status status = isPresent ? Status.PERFORMED : Status.CANCELED;
        bid.setStatus(status);
        bidRepository.save(bid);
        return bid;
    }
}
