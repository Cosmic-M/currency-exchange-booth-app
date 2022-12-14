package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.request.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.response.BuyAmountResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.response.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.response.DealStatusResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DealMapperTest {
    private static final String MESSAGE_STATUS_PERFORMED = "Deal confirmed: Status.PERFORMED";
    private static final String MESSAGE_STATUS_CANCELED = "Wrong password: Status.CANCELED";
    @InjectMocks
    private DealMapper dealMapper;
    private Deal deal;
    private DealRequestDto dealRequestDto;

    @BeforeEach
    void setUp() {
        deal = new Deal();
        deal.setId(10L);
        deal.setCcySale("EUR");
        deal.setCcyBuy("UAH");
        deal.setCcySaleAmount(BigDecimal.valueOf(500));
        deal.setCcyBuyAmount(BigDecimal.valueOf(20200));
        deal.setDateTime(LocalDateTime
                .of(2022, 9, 6, 12, 30, 15, 0));
        deal.setPhone("+380951001020");
        deal.setStatus(Status.PERFORMED);

        dealRequestDto = new DealRequestDto();
        dealRequestDto.setCcySale("EUR");
        dealRequestDto.setCcyBuy("UAH");
        dealRequestDto.setCcySaleAmount(BigDecimal.valueOf(500));
        dealRequestDto.setPhone("+380951001020");

        DealResponseDto dealResponseDto = new DealResponseDto();
        dealResponseDto.setCcyBuyAmount(BigDecimal.valueOf(20200));
        dealResponseDto.setPhone("+380951001020");
    }

    @Test
    void mapToModel_ok() {
        Deal result = dealMapper.toModel(dealRequestDto);
        Assertions.assertEquals("EUR", result.getCcySale());
        Assertions.assertEquals("UAH", result.getCcyBuy());
        Assertions.assertEquals(BigDecimal.valueOf(500), result.getCcySaleAmount());
        Assertions.assertEquals("+380951001020", result.getPhone());
    }

    @Test
    void mapToDto_ok() {
        DealResponseDto result = dealMapper.toDto(deal);
        Assertions.assertEquals("EUR", result.getCcySale());
        Assertions.assertEquals("UAH", result.getCcyBuy());
        Assertions.assertEquals(BigDecimal.valueOf(500), result.getCcySaleAmount());
        Assertions.assertEquals(BigDecimal.valueOf(20200), result.getCcyBuyAmount());
        Assertions.assertEquals("+380951001020", result.getPhone());
        Assertions.assertEquals(LocalDateTime
                .of(2022, 9, 6, 12, 30, 15, 0), deal.getDateTime());
        Assertions.assertEquals(Status.PERFORMED, deal.getStatus());
    }

    @Test
    void toBuyAmountDto_ok() {
        BuyAmountResponseDto result = dealMapper.toBuyAmountDto(deal);
        Assertions.assertEquals(BigDecimal.valueOf(20200), result.getCcyBuyAmount());
        Assertions.assertEquals("+380951001020", result.getPhone());
    }

    @Test
    void toDealStatusDto_statusConfirmed_ok() {
        DealStatusResponseDto result = dealMapper.toDealStatusDto(deal);
        Assertions.assertEquals(MESSAGE_STATUS_PERFORMED, result.getMessage());
    }

    @Test
    void toDealStatusDto_statusCanceled_ok() {
        deal.setStatus(Status.CANCELED);
        DealStatusResponseDto result = dealMapper.toDealStatusDto(deal);
        Assertions.assertEquals(MESSAGE_STATUS_CANCELED, result.getMessage());
    }
}
