package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.ExchangeRate;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    @Query("SELECT er.sale FROM ExchangeRate er WHERE er.dateTime > CURRENT_DATE AND er.ccy = ?1")
    BigDecimal getCcySale(String ccy);
}