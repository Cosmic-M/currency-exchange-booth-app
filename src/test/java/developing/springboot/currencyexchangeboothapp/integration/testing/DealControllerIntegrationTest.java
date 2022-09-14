package developing.springboot.currencyexchangeboothapp.integration.testing;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import developing.springboot.currencyexchangeboothapp.CurrencyExchangeBoothAppApplication;
import developing.springboot.currencyexchangeboothapp.dto.response.BuyAmountResponseDto;
import developing.springboot.currencyexchangeboothapp.dto.response.DealStatusResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Status;
import developing.springboot.currencyexchangeboothapp.repository.DealRepository;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import developing.springboot.currencyexchangeboothapp.repository.OtpPasswordRepository;
import developing.springboot.currencyexchangeboothapp.service.SmsSender;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CurrencyExchangeBoothAppApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
class DealControllerIntegrationTest {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private OtpPasswordRepository otpPasswordRepository;
    @MockBean
    private SmsSender smsSender;

    @Test
    @Sql(value = {"/create-exchange-rates-before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-exchange-rates-after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void expectForCorrectRespondCreatingNewDeal() throws Exception {
        doNothing().when(smsSender).sendSms(anyString(), anyString());
        Map<String,String> body = new HashMap<>();
        body.put("ccySale", "USD");
        body.put("ccyBuy", "UAH");
        body.put("ccySaleAmount", "100");
        body.put("phone", "+380501234567");
        String jsonString = gson.toJson(body);
        String jsonResponseString = mockMvc.perform(post("/deals/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BuyAmountResponseDto buyAmountResponseDto
                = gson.fromJson(jsonResponseString, BuyAmountResponseDto.class);
        Assertions.assertEquals(body.get("phone"), buyAmountResponseDto.getPhone());
        Assertions.assertEquals(exchangeRateRepository.getCcySaleForCurrency(body.get("ccySale"))
                .multiply(BigDecimal.valueOf(Long.parseLong(body.get("ccySaleAmount")))),
                buyAmountResponseDto.getCcyBuyAmount());
    }

    @Test
    @Sql(value = {"/create-exchange-rates-before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-exchange-rates-after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void expectForBadRequestBecauseOfPhoneNumberIsNotValid() throws Exception {
        doNothing().when(smsSender).sendSms(anyString(), anyString());
        Map<String,String> body = new HashMap<>();
        body.put("ccySale", "USD");
        body.put("ccyBuy", "UAH");
        body.put("ccySaleAmount", "100");
        body.put("phone", "(050) 345-67-89");
        String jsonString = gson.toJson(body);
        this.mockMvc.perform(post("/deals/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(value = {"/create-deals-before.sql", "/create-otp-password-before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-deals-after.sql", "/delete-otp-password-after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnExpectedRespondForValidOtp() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("password", "123456");
        body.put("phone", "+380501234567");
        String jsonString = gson.toJson(body);
        String jsonRespondString = mockMvc.perform(post("/deals/validate-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        DealStatusResponseDto dealStatusResponseDto
                = gson.fromJson(jsonRespondString, DealStatusResponseDto.class);

        Assertions.assertEquals("Deal confirmed: Status.PERFORMED",
                dealStatusResponseDto.getMessage());
        Assertions.assertEquals(Status.PERFORMED, dealRepository.findById(99L).orElseThrow(() ->
                new RuntimeException("Cannot get Deal entity by id=99 during testing validateOtp "
                        + "method from DealController")).getStatus());
        Assertions.assertFalse(otpPasswordRepository.findById(99L).isPresent());
    }

    @Test
    @Sql(value = {"/create-deals-before.sql", "/create-otp-password-before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-deals-after.sql", "/delete-otp-password-after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnStatusOkAfterDeletingDealFromDbByPhone() throws Exception {
        mockMvc.perform(delete("/deals/delete?phone=+380501234567"))
                .andExpect(status().isOk());
    }
}
