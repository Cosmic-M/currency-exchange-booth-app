package developing.springboot.currencyexchangeboothapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import developing.springboot.currencyexchangeboothapp.dto.response.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.util.SortDealsUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "developing.springboot.currencyexchangeboothapp")
@Sql(value = {"/create-deals-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-deals-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DealRepositoryTest {
    @Autowired
    private DealRepository dealRepository;

    @Test
    void getDailyReport_repoHasCorrespondData_ok() {
        List<ReportResponse> report = dealRepository.getDealsPerDay();
        Assertions.assertEquals(2, report.size());
        Assertions.assertEquals("USD", report.get(0).getCcySale());
        Assertions.assertEquals("EUR", report.get(1).getCcySale());
        Assertions.assertEquals(4, (int) report.get(0).getDealsCount());
        Assertions.assertEquals(6, (int) report.get(1).getDealsCount());
        Assertions.assertEquals(400, (int) report.get(0).getTotalCcySale());
        Assertions.assertEquals(600, (int) report.get(1).getTotalCcySale());
    }

    @Test
    void findAllByCcyAndPeriod_repoHasCorrespondData_ok() {
        Sort sort = SortDealsUtil.getSortingDeals("id");
        List<Deal> result = dealRepository.findAllByCcyAndPeriod("EUR",
                LocalDateTime.of(2022, 9, 9, 0, 0, 0),
                LocalDateTime.of(2022, 9, 10, 23, 59, 59),
                PageRequest.of(0, 20, sort));
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("+380951001020", result.get(0).getPhone());
        assertThat(result.get(0).getCcySaleAmount()).isEqualByComparingTo(BigDecimal.valueOf(100));
        assertThat(result.get(0).getCcyBuyAmount()).isEqualByComparingTo(BigDecimal.valueOf(4040));
    }

    @Test
    void findAllByCcyAndPeriod_repoHasNotCorrespondData_notOk() {
        Sort sort = SortDealsUtil.getSortingDeals("id");
        List<Deal> result = dealRepository.findAllByCcyAndPeriod("UAH",
                LocalDateTime.of(1980, 1, 1, 12, 0, 0),
                LocalDateTime.of(2022, 9, 10, 12, 0, 0),
                PageRequest.of(0, 20, sort));
        Assertions.assertEquals(0, result.size());
    }
}
