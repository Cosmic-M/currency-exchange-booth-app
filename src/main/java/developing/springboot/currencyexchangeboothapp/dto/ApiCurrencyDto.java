package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCurrencyDto {
    private String ccy;
    private String base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
