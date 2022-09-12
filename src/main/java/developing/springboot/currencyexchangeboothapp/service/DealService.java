package developing.springboot.currencyexchangeboothapp.service;

import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface DealService {
    Deal create(Deal deal);

    void deleteDealBy(String phone);

    List<ReportResponse> doReport();

    List<Deal> findAllByCcyAndPeriod(String ccy,
                                     LocalDate from,
                                     LocalDate to,
                                     PageRequest pageRequest);
}
