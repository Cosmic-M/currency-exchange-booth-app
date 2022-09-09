package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealResponseDto {
    private BigDecimal ccyBuyAmount;
    private String phone;
}
