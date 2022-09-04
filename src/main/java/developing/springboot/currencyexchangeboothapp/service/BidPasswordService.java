package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Bid;
import developing.springboot.currencyexchangeboothapp.model.BidIdPassword;

public interface BidPasswordService {
    Bid passwordValidation(BidIdPassword bidIdPassword);
}
