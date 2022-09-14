package developing.springboot.currencyexchangeboothapp.dto.response;

import developing.springboot.currencyexchangeboothapp.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
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
