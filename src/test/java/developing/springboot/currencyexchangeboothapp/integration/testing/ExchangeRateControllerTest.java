package developing.springboot.currencyexchangeboothapp.integration.testing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import developing.springboot.currencyexchangeboothapp.CurrencyExchangeBoothAppApplication;
import developing.springboot.currencyexchangeboothapp.dto.ExchangeRateResponseDto;
import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import developing.springboot.currencyexchangeboothapp.repository.ExchangeRateRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@Sql(value = {"/create-exchange-rates-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-exchange-rates-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExchangeRateControllerTest {
    private static final Integer NOT_ERASED_ROW_COUNT = 1;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    void openWorkDayShouldReturnValidJson() throws Exception {
        String jsonString = this.mockMvc.perform(get("/exchange-rates/open-work-day"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ExchangeRateResponseDto[] exchangeRateResponseDto
                = gson.fromJson(jsonString, ExchangeRateResponseDto[].class);
        Assertions.assertNotNull(exchangeRateResponseDto);
        Assertions.assertTrue(exchangeRateResponseDto.length > 0);
        Assertions.assertNotNull(exchangeRateResponseDto[0].getCcy());
        Assertions.assertNotNull(exchangeRateResponseDto[0].getBaseCcy());
        Assertions.assertNotNull(exchangeRateResponseDto[0].getBuy());
        Assertions.assertNotNull(exchangeRateResponseDto[0].getSale());
        Assertions.assertNotNull(exchangeRateResponseDto[0].getDateTime());
    }

    @Test
    void openWorkDayShouldCorrectlySafeDataIntoDB() throws Exception {
        String jsonString = this.mockMvc.perform(get("/exchange-rates/open-work-day"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ExchangeRateResponseDto[] exchangeRateResponseDto
                = gson.fromJson(jsonString, ExchangeRateResponseDto[].class);

        int jsonArrSize = exchangeRateResponseDto.length;
        List<ExchangeRate> list = exchangeRateRepository.findAll();
        Assertions.assertEquals(jsonArrSize, list.size() - NOT_ERASED_ROW_COUNT);
    }
}
