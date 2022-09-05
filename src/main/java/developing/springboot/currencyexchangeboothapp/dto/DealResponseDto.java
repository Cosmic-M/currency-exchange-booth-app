package developing.springboot.currencyexchangeboothapp.dto;

import developing.springboot.currencyexchangeboothapp.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealResponseDto {
    private Long id;
    private String ccySale;
    private String ccyBuy;
    private BigDecimal ccySaleAmount;
    private BigDecimal ccyBuyAmount;
    private String phone;
    private LocalDateTime dateTime;
    private Status status;
}
