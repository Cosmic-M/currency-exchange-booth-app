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
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.restassured.parsing.Parser;
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
        requestDto.setPhone("(050) 500-50-05");

        Deal inputDeal = new Deal();
        inputDeal.setCcySale("USD");
        inputDeal.setCcyBuy("UAH");
        inputDeal.setCcySaleAmount(BigDecimal.valueOf(1000));
        inputDeal.setPhone("(050) 500-50-05");
        inputDeal.setDateTime(LocalDateTime.of(1991,8,24,15,30,0));
        inputDeal.setStatus(Status.NEW);

        Deal createdDeal = new Deal();
        createdDeal.setId(1L);
        createdDeal.setCcySale("USD");
        createdDeal.setCcyBuy("UAH");
        createdDeal.setCcySaleAmount(BigDecimal.valueOf(1000));
        createdDeal.setCcyBuyAmount(BigDecimal.valueOf(399000));
        createdDeal.setPhone("(050) 500-50-05");
        createdDeal.setDateTime(LocalDateTime.of(1991,8,24,15,30,0));
        createdDeal.setStatus(Status.NEW);

        DealResponseDto responseDto = new DealResponseDto();
        responseDto.setCcyBuyAmount(BigDecimal.valueOf(399000));
        responseDto.setPhone("(050) 500-50-05");

        Mockito.when(dealMapper.toModel(requestDto)).thenReturn(inputDeal);
        Mockito.when(dealService.create(inputDeal)).thenReturn(createdDeal);
        Mockito.when(otpPasswordService.create(createdDeal)).thenReturn(new OtpPassword());
        Mockito.when(dealMapper.toDto(createdDeal)).thenReturn(responseDto);

        RestAssured.registerParser("text/plain", Parser.JSON);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new DealRequestDto(requestDto.getCcySale(), requestDto.getCcyBuy(), requestDto.getCcySaleAmount(), requestDto.getPhone()))
                .when()
                .post("/deal/create")
                .then()
                .statusCode(200)
                .body("ccyBuyAmount", Matchers.equalTo(39900))
                .body("phone", Matchers.equalTo("(050) 500-50-05"));
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

        Mockito.when(otpPasswordMapper.toModel(requestDto)).thenReturn(otpPassword);
        Mockito.when(otpPasswordService.passwordValidation(otpPassword)).thenReturn(deal);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("/deal/validate-otp")
                .then()
                .statusCode(200)
                .body("content", Matchers.equalTo("password is correct"));
    }

    @Test
    void delete() {
    }
}
