package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.BidPasswordRequest;
import developing.springboot.currencyexchangeboothapp.model.BidIdPassword;
import org.springframework.stereotype.Component;

@Component
public class BidIdPasswordMapper {
    public BidIdPassword toModel(BidPasswordRequest passwordRequest) {
        BidIdPassword bidIdPassword = new BidIdPassword();
        bidIdPassword.setId(passwordRequest.getId());
        bidIdPassword.setPassword(passwordRequest.getPassword());
        return bidIdPassword;
    }
}
