package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.dto.ReportResponse;
import developing.springboot.currencyexchangeboothapp.model.Deal;
import developing.springboot.currencyexchangeboothapp.model.Status;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
    Deal findDealByPhoneAndStatus(String phone, Status status);

    @Query(value = "SELECT d.ccy_sale as ccySale, d.ccy_buy as ccyBuy, COUNT(d.id) as dealsCount, "
            + "SUM(d.ccy_sale_amount) as totalCcySale "
            + "FROM deals d "
            + "WHERE d.date_time > CURRENT_DATE AND d.status = 'PERFORMED' "
            + "GROUP BY ccySale, ccyBuy "
            + "ORDER BY dealsCount ASC ", nativeQuery = true)
    List<ReportResponse> getDealsPerDay();

    @Query("FROM Deal d WHERE d.ccySale = ?1 AND d.dateTime BETWEEN ?2 AND ?3 ORDER BY d.dateTime")
    List<Deal> findAllByCcyAndPeriod(String ccyBuy,
                                     LocalDateTime from,
                                     LocalDateTime to,
                                     Pageable pageable);
}
