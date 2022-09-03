package developing.springboot.currencyexchangeboothapp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "bid")
public class Bid {
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
