package developing.springboot.currencyexchangeboothapp.service.impl;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import developing.springboot.currencyexchangeboothapp.service.SmsSender;
import developing.springboot.currencyexchangeboothapp.twilio.config.TwilioConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("twilio")
@RequiredArgsConstructor
public class TwilioSmsSenderImpl implements SmsSender {
    private final TwilioConfiguration twilioConfiguration;

    @Override
    public void sendSms(String otp, String phoneNumber) {
        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String otpMessage = "Your OTP is ##" + otp
                + "##. Report this passcode to complete your transaction operation. Good Luck!";
        MessageCreator creator = Message.creator(to, from, otpMessage);
        creator.create();
    }
}
