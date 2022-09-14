package developing.springboot.currencyexchangeboothapp.integration.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import developing.springboot.currencyexchangeboothapp.CurrencyExchangeBoothAppApplication;
import developing.springboot.currencyexchangeboothapp.dto.Operation;
import developing.springboot.currencyexchangeboothapp.dto.response.ReportResponseDto;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Sql(value = {"/create-deals-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-deals-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ReportControllerIntegrationTest {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void doReportShouldReturnExpectedJsonMessage() throws Exception {
        String jsonString = this.mockMvc.perform(get("/report"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ReportResponseDto[] responseDtoList
                = new Gson().fromJson(jsonString, ReportResponseDto[].class);
        Assertions.assertEquals(2, responseDtoList.length);
        Assertions.assertEquals("USD", responseDtoList[0].getCurrency());
        Assertions.assertEquals(Operation.SOLD, responseDtoList[0].getOperation());
        Assertions.assertEquals(4, responseDtoList[0].getDealsCount());
        Assertions.assertEquals(400, responseDtoList[0].getTotalSum());
        Assertions.assertEquals("EUR", responseDtoList[1].getCurrency());
        Assertions.assertEquals(Operation.SOLD, responseDtoList[1].getOperation());
        Assertions.assertEquals(6, responseDtoList[1].getDealsCount());
        Assertions.assertEquals(600, responseDtoList[1].getTotalSum());
    }

    @Test
    void getDealsShouldReturnExpectedJsonMessage() throws Exception {
        String jsonString = this.mockMvc.perform(get("/ccy-period"
                + "?ccySale=USD&from=2022-09-09&to=2022-09-10&count=20&page=0&sortBy=id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Deal[] deals = gson.fromJson(jsonString, Deal[].class);
        Assertions.assertEquals(2, deals.length);

        Assertions.assertEquals("USD", deals[0].getCcySale());
        Assertions.assertEquals("UAH", deals[0].getCcyBuy());
        assertThat(deals[0].getCcySaleAmount()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(deals[0].getCcyBuyAmount()).isEqualByComparingTo(BigDecimal.valueOf(3990));
        Assertions.assertEquals("+380951001021", deals[0].getPhone());
        Assertions.assertEquals(LocalDateTime.of(2022, 9, 9, 8, 25, 21), deals[0].getDateTime());
        Assertions.assertEquals(Status.PERFORMED, deals[0].getStatus());

        Assertions.assertEquals("USD", deals[1].getCcySale());
        Assertions.assertEquals("UAH", deals[1].getCcyBuy());
        assertThat(deals[1].getCcySaleAmount()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(deals[1].getCcyBuyAmount()).isEqualByComparingTo(BigDecimal.valueOf(3990));
        Assertions.assertEquals("+380951001021", deals[1].getPhone());
        Assertions.assertEquals(LocalDateTime.of(2022, 9, 10, 10, 25, 21), deals[1].getDateTime());
        Assertions.assertEquals(Status.PERFORMED, deals[1].getStatus());
    }
}
