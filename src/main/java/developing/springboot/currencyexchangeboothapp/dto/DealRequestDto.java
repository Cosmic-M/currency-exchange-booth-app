package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealRequestDto {
    private String ccySale;
    private String ccyBuy;
    private BigDecimal ccySaleAmount;
    private String phone;
}
