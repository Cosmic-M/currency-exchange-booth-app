package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.*;
import developing.springboot.currencyexchangeboothapp.service.SmsSender;
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

import javax.validation.Valid;

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
    public BuyAmountResponseDto createDeal(@RequestBody @Valid DealRequestDto requestDto) {
        Deal newDeal = dealMapper.toModel(requestDto);
        newDeal = dealService.create(newDeal);
        OtpPassword otpPassword = otpPasswordService.create(newDeal);
        //smsSender.sendSms(otpPassword.getPassword(), newDeal.getPhone());
        return dealMapper.toBuyAmountDto(newDeal);
    }

    @PostMapping("/validate-otp")
    public DealStatusResponseDto validateOtp(@RequestBody PasswordRequestDto passwordRequestDto) {
        OtpPassword otpPassword = otpPasswordMapper.toModel(passwordRequestDto);
        Deal deal = otpPasswordService.passwordValidation(otpPassword);
        return dealMapper.toDealStatusDto(deal);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String phone) {
        dealService.deleteDealBy(phone);
    }
}
