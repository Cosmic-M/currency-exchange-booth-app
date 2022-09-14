package developing.springboot.currencyexchangeboothapp.service.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
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
class OtpPasswordServiceImplTest {
    @Autowired
    private OtpPasswordServiceImpl otpPasswordService;
    @MockBean
    private DealRepository dealRepository;
    @MockBean
    private OtpPasswordRepository otpPasswordRepository;
    @Autowired
    private MockMvc mockMvc;
    private String password;
    private String phone;
    private Deal deal;
    private OtpPassword otpPassword;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        password = "123456";
        phone = "+380951234567";

        deal = new Deal();
        deal.setId(10L);
        deal.setCcySale("USD");
        deal.setCcyBuy("UAH");
        deal.setCcySaleAmount(BigDecimal.valueOf(100));
        deal.setCcyBuyAmount(BigDecimal.valueOf(3990));
        deal.setPhone("+380951234567");
        deal.setDateTime(LocalDateTime.of(2016,8,9,13,30));
        deal.setStatus(Status.NEW);

        otpPassword = new OtpPassword();
        otpPassword.setId(10L);
        otpPassword.setPassword("123456");
    }

    @Test
    void passwordValidation_validPasswordAndPhone_ok() {
        Mockito.when(dealRepository.findDealByPhoneAndStatus(phone, Status.NEW)).thenReturn(deal);
        Mockito.when(otpPasswordRepository.findByPassword(password)).thenReturn(otpPassword);
        Mockito.when(dealRepository.save(deal)).thenReturn(deal);
        doNothing().when(otpPasswordRepository).deleteById(anyLong());

        Deal result = otpPasswordService.passwordValidation(password, phone);
        Assertions.assertEquals(Status.PERFORMED, result.getStatus());
    }

    @Test
    void passwordValidation_passwordIsNotValid_notOk() {
        Mockito.when(dealRepository.findDealByPhoneAndStatus(phone, Status.NEW)).thenReturn(deal);
        Mockito.when(otpPasswordRepository.findByPassword(password)).thenReturn(null);
        Mockito.when(dealRepository.save(deal)).thenReturn(deal);
        doNothing().when(otpPasswordRepository).deleteById(anyLong());

        Deal result = otpPasswordService.passwordValidation(password, phone);
        Assertions.assertEquals(Status.CANCELED, result.getStatus());
    }
}
