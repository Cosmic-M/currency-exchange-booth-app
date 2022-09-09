package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DealRequestDto {
    private String ccySale;
    private String ccyBuy;
    private BigDecimal ccySaleAmount;
    private String phone;
}
