package developing.springboot.currencyexchangeboothapp.controller;

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
import lombok.NonNull;
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
    @NonNull private DealService dealService;
    @NonNull private DealMapper dealMapper;
    @NonNull private OtpPasswordMapper otpPasswordMapper;
    @NonNull private OtpPasswordService otpPasswordService;

    @PostMapping("/create")
    public DealResponseDto createBid(@RequestBody DealRequestDto requestDto) {
        Deal newDeal = dealMapper.toModel(requestDto);
        newDeal = dealService.create(newDeal);
        return dealMapper.toDto(newDeal);
    }

    @PostMapping("/confirm")
    public String confirmBid(@RequestBody PasswordRequestDto passwordRequestDto) {
        OtpPassword otpPassword = otpPasswordMapper.toModel(passwordRequestDto);
        Deal deal = otpPasswordService.passwordValidation(otpPassword);
        return deal.getStatus() == Status.PERFORMED ? "password is correct" : "wrong password";
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String phone) {
        dealService.deleteDealBy(phone);
    }
}
