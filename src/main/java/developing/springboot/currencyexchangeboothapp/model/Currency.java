package developing.springboot.currencyexchangeboothapp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name ="currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ccy;
    @Column(name = "base_ccy")
    private String baseCcy;
    @Column(precision = 16, scale = 4)
    private BigDecimal buy;
    @Column(precision = 16, scale = 4)
    private BigDecimal sale;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
