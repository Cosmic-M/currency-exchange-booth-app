package developing.springboot.currencyexchangeboothapp;

import developing.springboot.currencyexchangeboothapp.model.OtpPassword;

public interface SmsSender {

    void sendSms(OtpPassword otpPassword, String phoneNumber);

    // or maybe void sendSms(String phoneNumber, String message);
}