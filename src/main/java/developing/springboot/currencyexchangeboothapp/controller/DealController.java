package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.SmsSender;
import developing.springboot.currencyexchangeboothapp.dto.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.PasswordRequestDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.service.DealService;
import developing.springboot.currencyexchangeboothapp.service.OtpPasswordService;
import developing.springboot.currencyexchangeboothapp.service.mapper.DealMapper;
import developing.springboot.currencyexchangeboothapp.service.mapper.OtpPasswordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal")
public class DealController {
    private final DealService dealService;
    private final DealMapper dealMapper;
    private final OtpPasswordMapper otpPasswordMapper;
    private final OtpPasswordService otpPasswordService;
    private final SmsSender smsSender;

    @PostMapping("/create")
    public DealResponseDto createDeal(@RequestBody DealRequestDto requestDto) {
        Deal newDeal = dealMapper.toModel(requestDto);
        newDeal = dealService.create(newDeal);
        OtpPassword otpPassword = otpPasswordService.create(newDeal);
        smsSender.sendSms(otpPassword, newDeal.getPhone());
        return dealMapper.toDto(newDeal);
    }

    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody PasswordRequestDto passwordRequestDto) {
        OtpPassword otpPassword = otpPasswordMapper.toModel(passwordRequestDto);
        Deal deal = otpPasswordService.passwordValidation(otpPassword);
        return deal.getStatus() == Status.PERFORMED ? "password is correct" : "wrong password";
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String phone) {
        dealService.deleteDealBy(phone);
    }
}
