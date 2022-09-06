package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.PasswordRequestDto;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import org.springframework.stereotype.Component;

@Component
public class OtpPasswordMapper {
    public OtpPassword toModel(PasswordRequestDto passwordRequestDto) {
        OtpPassword otpPassword = new OtpPassword();
        otpPassword.setId(passwordRequestDto.getId());
        otpPassword.setPassword(passwordRequestDto.getPassword());
        return otpPassword;
    }
}
