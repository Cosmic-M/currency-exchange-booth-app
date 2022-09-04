package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.BidRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.BidResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Bid;
import developing.springboot.currencyexchangeboothapp.model.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BidMapper {
    public Bid toModel(BidRequestDto requestDto) {
        Bid bid = new Bid();
        bid.setCcyBuy(requestDto.getCcyBuy());
        bid.setCcySale(requestDto.getCcySale());
        bid.setCcySaleAmount(requestDto.getCcySaleAmount());
        bid.setPhone(requestDto.getPhone());
        bid.setDateTime(LocalDateTime.now());
        bid.setStatus(Status.NEW);
        return bid;
    }

    public BidResponseDto toDto(Bid bid) {
        BidResponseDto responseDto = new BidResponseDto();
        responseDto.setId(bid.getId());
        responseDto.setCcySale(bid.getCcySale());
        responseDto.setCcyBuy(bid.getCcyBuy());
        responseDto.setCcySaleAmount(bid.getCcySaleAmount());
        responseDto.setCcyBuyAmount(bid.getCcyBuyAmount());
        responseDto.setPhone(bid.getPhone());
        responseDto.setDateTime(bid.getDateTime());
        responseDto.setStatus(bid.getStatus());
        return responseDto;
    }
}
