package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {
    public Deal toModel(DealRequestDto requestDto) {
        Deal bid = new Deal();
        bid.setCcyBuy(requestDto.getCcyBuy());
        bid.setCcySale(requestDto.getCcySale());
        bid.setCcySaleAmount(requestDto.getCcySaleAmount());
        bid.setPhone(requestDto.getPhone());
        bid.setDateTime(LocalDateTime.now());
        bid.setStatus(Status.NEW);
        return bid;
    }

    public DealResponseDto toDto(Deal bid) {
        DealResponseDto responseDto = new DealResponseDto();
        responseDto.setCcyBuyAmount(bid.getCcyBuyAmount());
        responseDto.setPhone(bid.getPhone());
        return responseDto;
    }
}
