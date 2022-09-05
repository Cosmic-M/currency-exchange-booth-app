package developing.springboot.currencyexchangeboothapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ccy_sale")
    private String ccySale;
    @Column(name = "ccy_buy")
    private String ccyBuy;
    @Column(name = "ccy_sale_amount")
    private BigDecimal ccySaleAmount;
    @Column(name = "ccy_buy_amount")
    private BigDecimal ccyBuyAmount;
    private String phone;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private Status status;
}
