package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.BuyAmountResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.DealStatusResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {
    public Deal toModel(DealRequestDto requestDto) {
        Deal deal = new Deal();
        deal.setCcyBuy(requestDto.getCcyBuy());
        deal.setCcySale(requestDto.getCcySale());
        deal.setCcySaleAmount(requestDto.getCcySaleAmount());
        deal.setPhone(requestDto.getPhone());
        deal.setDateTime(LocalDateTime.now());
        deal.setStatus(Status.NEW);
        return deal;
    }

    public DealResponseDto toDto(Deal deal) {
        DealResponseDto responseDto = new DealResponseDto();
        responseDto.setId(deal.getId());
        responseDto.setCcySale(deal.getCcySale());
        responseDto.setCcyBuy(deal.getCcyBuy());
        responseDto.setCcySaleAmount(deal.getCcySaleAmount());
        responseDto.setCcyBuyAmount(deal.getCcyBuyAmount());
        responseDto.setPhone(deal.getPhone());
        responseDto.setDateTime(deal.getDateTime());
        responseDto.setStatus(deal.getStatus());
        return responseDto;
    }

    public BuyAmountResponseDto toBuyAmountDto(Deal deal) {
        BuyAmountResponseDto responseDto = new BuyAmountResponseDto();
        responseDto.setCcyBuyAmount(deal.getCcyBuyAmount());
        responseDto.setPhone(deal.getPhone());
        return responseDto;
    }

    public DealStatusResponseDto toDealStatusDto(Deal deal) {
        DealStatusResponseDto responseDto = new DealStatusResponseDto();
        String message = deal.getStatus() == Status.PERFORMED
                ? "Deal confirmed: Status.PERFORMED" : "Wrong password: Status.CANCELED";
        responseDto.setMessage(message);
        return responseDto;
    }
}
