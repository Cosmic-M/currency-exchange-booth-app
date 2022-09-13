package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    @Query("SELECT er.sale FROM ExchangeRate er WHERE er.dateTime > CURRENT_DATE AND er.ccy = ?1")
    BigDecimal getCcySaleForCurrency(String ccy);

    @Query("SELECT er.buy FROM ExchangeRate er WHERE er.dateTime > CURRENT_DATE AND er.ccy = ?1")
    BigDecimal getCcySaleForNationCurrency(String ccy);

    ExchangeRate getByCcyAndBaseCcyAndDateTimeBetween(String ccy,
                                                      String baseCCy,
                                                      LocalDateTime from,
                                                      LocalDateTime to);
}
