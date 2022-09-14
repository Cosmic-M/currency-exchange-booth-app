package developing.springboot.currencyexchangeboothapp.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class ExchangeRateResponseDto {
    private Long id;
    private String ccy;
    private String baseCcy;
    private BigDecimal buy;
    private BigDecimal sale;
    private LocalDateTime dateTime;
}
