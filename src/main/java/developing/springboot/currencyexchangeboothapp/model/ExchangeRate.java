package developing.springboot.currencyexchangeboothapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ccy;
    @Column(name = "base_ccy")
    private String baseCcy;
    @Column(precision = 16, scale = 6)
    private BigDecimal buy;
    @Column(precision = 16, scale = 6)
    private BigDecimal sale;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
