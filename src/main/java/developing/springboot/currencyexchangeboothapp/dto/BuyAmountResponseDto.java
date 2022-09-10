package developing.springboot.currencyexchangeboothapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class BuyAmountResponseDto {
    private BigDecimal ccyBuyAmount;
    private String phone;
}
