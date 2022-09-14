package developing.springboot.currencyexchangeboothapp.dto.response;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BuyAmountResponseDto {
    private BigDecimal ccyBuyAmount;
    private String phone;
}
