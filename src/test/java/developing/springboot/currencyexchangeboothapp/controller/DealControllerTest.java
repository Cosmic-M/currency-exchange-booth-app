package developing.springboot.currencyexchangeboothapp.controller;

import developing.springboot.currencyexchangeboothapp.dto.BuyAmountResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.DealResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.DealRequestDto;
import developing.springboot.currencyexchangeboothapp.dto.DealStatusResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.PasswordRequestDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.service.DealService;
import developing.springboot.currencyexchangeboothapp.service.OtpPasswordService;
import developing.springboot.currencyexchangeboothapp.service.mapper.DealMapper;
import developing.springboot.currencyexchangeboothapp.service.mapper.OtpPasswordMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DealControllerTest {
    @MockBean
    private DealService dealService;
    @MockBean
    private DealMapper dealMapper;
    @MockBean
    private OtpPasswordService otpPasswordService;
    @MockBean
    private OtpPasswordMapper otpPasswordMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateDeal_correctData_ok() {
        DealRequestDto requestDto = new DealRequestDto();
        requestDto.setCcySale("USD");
        requestDto.setCcyBuy("UAH");
        requestDto.setCcySaleAmount(BigDecimal.valueOf(1000));
        requestDto.setPhone("+380505005050");

        Deal inputDeal = new Deal();
        inputDeal.setCcySale("USD");
        inputDeal.setCcyBuy("UAH");
        inputDeal.setCcySaleAmount(BigDecimal.valueOf(1000));
        inputDeal.setPhone("+380505005050");
        inputDeal.setDateTime(LocalDateTime.of(1991,8,24,15,30,0));
        inputDeal.setStatus(Status.NEW);

        Deal createdDeal = new Deal();
        createdDeal.setId(1L);
        createdDeal.setCcySale("USD");
        createdDeal.setCcyBuy("UAH");
        createdDeal.setCcySaleAmount(BigDecimal.valueOf(1000));
        createdDeal.setCcyBuyAmount(BigDecimal.valueOf(39900));
        createdDeal.setPhone("+380505005050");
        createdDeal.setDateTime(LocalDateTime.of(1991,8,24,15,30,0));
        createdDeal.setStatus(Status.NEW);

        DealResponseDto responseDto = new DealResponseDto();
        responseDto.setCcyBuyAmount(BigDecimal.valueOf(39900));
        responseDto.setPhone("+380505005050");

        BuyAmountResponseDto buyAmountResponseDto = new BuyAmountResponseDto();
        buyAmountResponseDto.setCcyBuyAmount(BigDecimal.valueOf(39900));
        buyAmountResponseDto.setPhone("+380505005050");

        Mockito.when(dealMapper.toModel(requestDto)).thenReturn(inputDeal);
        Mockito.when(dealService.create(inputDeal)).thenReturn(createdDeal);
        Mockito.when(otpPasswordService.create(createdDeal)).thenReturn(new OtpPassword());
        Mockito.when(dealMapper.toDto(createdDeal)).thenReturn(responseDto);
        Mockito.when(dealMapper.toBuyAmountDto(createdDeal)).thenReturn(buyAmountResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new DealRequestDto(requestDto.getCcySale(), requestDto.getCcyBuy(), requestDto.getCcySaleAmount(), requestDto.getPhone()))
                .when()
                .post("/deal/create")
                .then()
                .statusCode(200)
                .body("ccyBuyAmount", Matchers.equalTo(39900))
                .body("phone", Matchers.equalTo("+380505005050"));
    }

    @Test
    void confirmDeal_correctOtpPassword_ok() {
        PasswordRequestDto requestDto = new PasswordRequestDto();
        requestDto.setId(15L);
        requestDto.setPassword("123456");

        OtpPassword otpPassword = new OtpPassword();
        otpPassword.setId(15L);
        otpPassword.setPassword("123456");

        Deal deal = new Deal();
        deal.setId(15L);
        deal.setCcySale("USD");
        deal.setCcyBuy("UAH");
        deal.setCcySaleAmount(BigDecimal.valueOf(1000));
        deal.setCcyBuyAmount(BigDecimal.valueOf(399000));
        deal.setPhone("(050) 500-50-05");
        deal.setDateTime(LocalDateTime.of(1991,8,24,15,30,0));
        deal.setStatus(Status.PERFORMED);

        DealStatusResponseDto dealStatusResponseDto = new DealStatusResponseDto();
        dealStatusResponseDto.setMessage("Deal confirmed: Status.PERFORMED");

        Mockito.when(otpPasswordMapper.toModel(requestDto)).thenReturn(otpPassword);
        Mockito.when(otpPasswordService.passwordValidation(otpPassword)).thenReturn(deal);
        Mockito.when(dealMapper.toDealStatusDto(deal)).thenReturn(dealStatusResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/deal/validate-otp")
                .then()
                .statusCode(200)
                .body("message", Matchers.equalTo("Deal confirmed: Status.PERFORMED"));
    }
}
