package developing.springboot.currencyexchangeboothapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ApiExchangeRateDto {
    private String ccy;
    @JsonProperty("base_ccy")
    private String baseCcy;
    private BigDecimal buy;
    private BigDecimal sale;
}
