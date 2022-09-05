package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.Currency;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Query("SELECT c.sale FROM Currency c WHERE c.dateTime > CURRENT_DATE AND c.ccy = ?1")
    BigDecimal getCcySale(String ccy);
}
