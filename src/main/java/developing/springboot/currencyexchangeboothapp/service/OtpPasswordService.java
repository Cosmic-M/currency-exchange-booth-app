package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;

public interface OtpPasswordService {
    Deal passwordValidation(OtpPassword bidIdPassword);
}
