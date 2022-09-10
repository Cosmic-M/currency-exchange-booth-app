package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ApiExchangeRateDto {
    private String ccy;
    private String base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
