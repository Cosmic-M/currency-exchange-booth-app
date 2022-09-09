package developing.springboot.currencyexchangeboothapp;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.Random;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(OtpPassword otpPassword, String phoneNumber) {
        String otp;
        if (isPhoneNumberValid(phoneNumber)) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String otpMessage = "Dear customer, Your OTP is ##" + otpPassword.getPassword() + "##. Use this passcode to complete your transaction. Good Luck!";
            MessageCreator creator = Message.creator(to, from, otpMessage);
            creator.create();
            LOGGER.info("Send sms on phoneNumber: {} with password:", otpPassword.getPassword());
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + phoneNumber + "] is not a valid number"
            );
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }
}