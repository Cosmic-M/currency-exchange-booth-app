package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.DealPasswordRequest;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import org.springframework.stereotype.Component;

@Component
public class OtpPasswordMapper {
    public OtpPassword toModel(DealPasswordRequest passwordRequest) {
        OtpPassword otpPassword = new OtpPassword();
        otpPassword.setId(passwordRequest.getId());
        otpPassword.setPassword(passwordRequest.getPassword());
        return otpPassword;
    }
}
