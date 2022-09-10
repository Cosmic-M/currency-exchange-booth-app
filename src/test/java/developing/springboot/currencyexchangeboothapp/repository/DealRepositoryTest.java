package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import developing.springboot.currencyexchangeboothapp.util.SortDealsUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = "developing.springboot.currencyexchangeboothapp")
class DealRepositoryTest {
    @Autowired
    private DealRepository dealRepository;
    private Deal deal1;
    private Deal deal2;
    private Deal deal3;
    private Deal deal4;
    private Deal deal5;

    @BeforeEach
    void setUp() {
        deal1 = new Deal();
        deal1.setCcySale("USD");
        deal1.setCcyBuy("UAH");
        deal1.setCcySaleAmount(BigDecimal.valueOf(100));
        deal1.setCcyBuyAmount(BigDecimal.valueOf(3990));
        deal1.setPhone("(050) 500-50-05");
        deal1.setDateTime(LocalDateTime.now());
        deal1.setStatus(Status.PERFORMED);

        deal2 = new Deal();
        deal2.setCcySale("EUR");
        deal2.setCcyBuy("UAH");
        deal2.setCcySaleAmount(BigDecimal.valueOf(100));
        deal2.setCcyBuyAmount(BigDecimal.valueOf(4040));
        deal2.setPhone("(050) 500-50-05");
        deal2.setDateTime(LocalDateTime.now());
        deal2.setStatus(Status.PERFORMED);

        deal3 = new Deal();
        deal3.setCcySale("USD");
        deal3.setCcyBuy("UAH");
        deal3.setCcySaleAmount(BigDecimal.valueOf(100));
        deal3.setCcyBuyAmount(BigDecimal.valueOf(3990));
        deal3.setPhone("(050) 500-50-05");
        deal3.setDateTime(LocalDateTime.now());
        deal3.setStatus(Status.PERFORMED);

        deal4 = new Deal();
        deal4.setCcySale("USD");
        deal4.setCcyBuy("UAH");
        deal4.setCcySaleAmount(BigDecimal.valueOf(100));
        deal4.setCcyBuyAmount(BigDecimal.valueOf(3990));
        deal4.setPhone("(050) 500-50-05");
        deal4.setDateTime(LocalDateTime.now());
        deal4.setStatus(Status.PERFORMED);

        deal5 = new Deal();
        deal5.setCcySale("EUR");
        deal5.setCcyBuy("UAH");
        deal5.setCcySaleAmount(BigDecimal.valueOf(100));
        deal5.setCcyBuyAmount(BigDecimal.valueOf(4040));
        deal5.setPhone("(050) 555-55-55");
        deal5.setDateTime(LocalDateTime.now());
        deal5.setStatus(Status.PERFORMED);
    }

    @AfterEach
    void clearRepo() {
        dealRepository.deleteAll();
    }

    @Test
    void deleteBy_repoHasCorrespondData_ok() {
        String phone = "(050) 500-50-05";
        deal1.setStatus(Status.NEW);
        dealRepository.save(deal1);
        dealRepository.deleteBy(phone);
        Assertions.assertEquals(0, dealRepository.findAll().size());
    }

    @Test
    void deleteBy_repoHasNotCorrespondData_notOk() {
        String phone = "(050) 500-50-05";
        dealRepository.save(deal1);
        dealRepository.deleteBy(phone);
        Assertions.assertEquals(1, dealRepository.findAll().size());
    }

    @Test
    void getDailyReport_repoHasCorrespondData_ok() {
        List<Deal> deals = new ArrayList<>();
        deals.add(deal1);
        deals.add(deal2);
        deals.add(deal3);
        deals.add(deal4);
        deals.add(deal5);
        dealRepository.saveAll(deals);
        List<ReportResponse> report = dealRepository.getDealsPerDay();
        Assertions.assertEquals(2, report.size());
        Assertions.assertEquals("EUR", report.get(0).getCcySale());
        Assertions.assertEquals("USD", report.get(1).getCcySale());
        Assertions.assertEquals(2, (int) report.get(0).getDealsCount());
        Assertions.assertEquals(3, (int) report.get(1).getDealsCount());
        Assertions.assertEquals(200, (int) report.get(0).getTotalCcySale());
        Assertions.assertEquals(300, (int) report.get(1).getTotalCcySale());
    }

    @Test
    void findAllByCcyAndPeriod_repoHasCorrespondData_ok() {
        List<Deal> deals = new ArrayList<>();
        deal1.setDateTime(LocalDateTime.of(2022, 9, 5, 10, 0, 0));
        deals.add(deal1);
        deal2.setDateTime(LocalDateTime.of(1991, 8, 24, 6, 0, 0));
        deals.add(deal2);
        deal3.setDateTime(LocalDateTime.of(2022, 9, 5, 10, 0, 0));
        deals.add(deal3);
        deal4.setDateTime(LocalDateTime.of(2022, 9, 5, 10, 0, 0));
        deals.add(deal4);
        deal5.setDateTime(LocalDateTime.of(2022, 9, 5, 10, 0, 0));
        deals.add(deal5);
        dealRepository.saveAll(deals);

        Sort sort = SortDealsUtil.getSortingDeals("id");

        List<Deal> result = dealRepository.findAllByCcyAndPeriod("EUR",
                LocalDateTime.of(2022, 9, 2, 12, 0, 0),
                LocalDateTime.of(2022, 9, 7, 23, 59, 59),
                PageRequest.of(0, 20, sort));
        Assertions.assertEquals(1, result.size());
        String phoneFromDb = result.get(0).getPhone();
        Assertions.assertEquals("(050) 555-55-55", phoneFromDb);
    }

    @Test
    void findAllByCcyAndPeriod_repoHasNotCorrespondData_notOk() {
        List<Deal> deals = new ArrayList<>();
        deal1.setDateTime(LocalDateTime.of(1991, 8, 24, 6, 0, 0));
        deals.add(deal1);
        deals.add(deal2);
        deals.add(deal3);
        dealRepository.saveAll(deals);

        Sort sort = SortDealsUtil.getSortingDeals("id");

        List<Deal> result = dealRepository.findAllByCcyAndPeriod("EUR",
                LocalDateTime.of(1980, 1, 1, 12, 0, 0),
                LocalDateTime.of(2020, 1, 1, 12, 0, 0),
                PageRequest.of(0, 20, sort));
        Assertions.assertEquals(0, result.size());
    }
}
