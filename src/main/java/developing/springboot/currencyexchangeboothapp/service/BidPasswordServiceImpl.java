package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Bid;
import developing.springboot.currencyexchangeboothapp.model.BidIdPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.repository.BidIdPasswordRepository;
import developing.springboot.currencyexchangeboothapp.repository.BidRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidPasswordServiceImpl implements BidPasswordService {
    @NonNull BidIdPasswordRepository bidIdPasswordRepository;
    @NonNull BidRepository bidRepository;

    @Override
    public Bid passwordValidation(BidIdPassword bidIdPassword) {
        boolean isPresent = bidIdPasswordRepository.exists(Example.of(bidIdPassword));
        Bid bid = bidRepository.findCoincidenceById(bidIdPassword.getId());
        bidIdPasswordRepository.delete(bidIdPassword);
        Status status = isPresent ? Status.PERFORMED : Status.CANCELED;
        bid.setStatus(status);
        bidRepository.save(bid);
        return bid;
    }
}
