package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CurrencyResponseDto {
    private Long id;
    private String ccy;
    private String baseCcy;
    private BigDecimal buy;
    private BigDecimal sale;
    private LocalDateTime dateTime;
}
