package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.request.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.request.PasswordPhoneRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.response.BuyAmountResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.response.DealStatusResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.service.DealService;
import developing.springboot.currencyexchangeboothapp.service.OtpPasswordService;
import developing.springboot.currencyexchangeboothapp.service.SmsSender;
import developing.springboot.currencyexchangeboothapp.service.mapper.DealMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {
    private final DealService dealService;
    private final DealMapper dealMapper;
    private final OtpPasswordService otpPasswordService;
    private final SmsSender smsSender;

    @ApiOperation(value = "method creates Deal with status 'NEW'. Please, "
            + "put valid phone number format (for example: +380501234567). "
            + "'USD', 'EUR', 'UAH' are valid for ccyBuy and ccySale")
    @PostMapping("/create")
    public BuyAmountResponseDto createDeal(@RequestBody @Valid DealRequestDto requestDto) {
        Deal newDeal = dealMapper.toModel(requestDto);
        newDeal = dealService.create(newDeal);
        OtpPassword otpPassword = otpPasswordService.create(newDeal);
        smsSender.sendSms(otpPassword.getPassword(), newDeal.getPhone());
        return dealMapper.toBuyAmountDto(newDeal);
    }

    @ApiOperation(value = "method determine by otp-password if phone number is valid")
    @PostMapping("/validate-otp")
    public DealStatusResponseDto validateOtp(
            @RequestBody @Valid PasswordPhoneRequestDto passwordRequestDto) {
        String phone = passwordRequestDto.getPhone();
        String password = passwordRequestDto.getPassword();
        Deal deal = otpPasswordService.passwordValidation(password, phone);
        return dealMapper.toDealStatusDto(deal);
    }

    @ApiOperation(value = "method delete deal with status 'NEW' by phone number")
    @DeleteMapping("/delete")
    public void delete(
            @RequestParam @ApiParam(value = "put valid phone number format "
            + "(for example: +380501234567)") @Valid String phone) {
        dealService.deleteDealBy(phone);
    }
}
