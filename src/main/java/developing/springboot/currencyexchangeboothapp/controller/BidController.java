package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.BidPasswordRequest;
import developing.springboot.currencyexchangeboothapp.dto.BidRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.BidResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Bid;
import developing.springboot.currencyexchangeboothapp.model.BidIdPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.service.BidPasswordService;
import developing.springboot.currencyexchangeboothapp.service.BidService;
import developing.springboot.currencyexchangeboothapp.service.mapper.BidIdPasswordMapper;
import developing.springboot.currencyexchangeboothapp.service.mapper.BidMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bid")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidController {
    @NonNull BidService bidService;
    @NonNull BidMapper bidMapper;
    @NonNull BidIdPasswordMapper bidIdPasswordMapper;
    @NonNull BidPasswordService bidPasswordService;

    @PostMapping("/create")
    public BidResponseDto createBid(@RequestBody BidRequestDto requestDto) {
        Bid newBid = bidMapper.toModel(requestDto);
        newBid = bidService.createBid(newBid);
        return bidMapper.toDto(newBid);
    }

    @PostMapping("/confirm")
    public String confirmBid(@RequestBody BidPasswordRequest passwordRequest) {
        BidIdPassword bidIdPassword = bidIdPasswordMapper.toModel(passwordRequest);
        Bid bid = bidPasswordService.passwordValidation(bidIdPassword);
        return bid.getStatus() == Status.PERFORMED ? "password is correct" : "wrong password";
    }
}
