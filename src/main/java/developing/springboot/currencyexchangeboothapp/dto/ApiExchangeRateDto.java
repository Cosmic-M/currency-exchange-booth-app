package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiExchangeRateDto {
    private String ccy;
    private String base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
