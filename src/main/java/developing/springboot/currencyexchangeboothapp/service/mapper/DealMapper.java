package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.request.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.response.BuyAmountResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.response.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.response.DealStatusResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {
    private static final String MESSAGE_STATUS_PERFORMED = "Deal confirmed: Status.PERFORMED";
    private static final String MESSAGE_STATUS_CANCELED = "Wrong password: Status.CANCELED";

    public DealStatusResponseDto toDealStatusDto(Deal deal) {
        DealStatusResponseDto responseDto = new DealStatusResponseDto();
        String message = deal.getStatus() == Status.PERFORMED
                ? MESSAGE_STATUS_PERFORMED : MESSAGE_STATUS_CANCELED;
        responseDto.setMessage(message);
        return responseDto;
    }

    public BuyAmountResponseDto toBuyAmountDto(Deal deal) {
        BuyAmountResponseDto responseDto = new BuyAmountResponseDto();
        responseDto.setCcyBuyAmount(deal.getCcyBuyAmount());
        responseDto.setPhone(deal.getPhone());
        return responseDto;
    }

    public Deal toModel(DealRequestDto requestDto) {
        Deal deal = new Deal();
        deal.setCcyBuy(requestDto.getCcyBuy());
        deal.setCcySale(requestDto.getCcySale());
        deal.setCcySaleAmount(requestDto.getCcySaleAmount());
        deal.setPhone(requestDto.getPhone());
        deal.setDateTime(ZonedDateTime.now().toLocalDateTime());
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
}
