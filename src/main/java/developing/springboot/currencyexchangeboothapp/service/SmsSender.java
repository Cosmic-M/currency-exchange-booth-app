package developing.springboot.currencyexchangeboothapp.service;

public interface SmsSender {

    void sendSms(String otp, String phoneNumber);
}
