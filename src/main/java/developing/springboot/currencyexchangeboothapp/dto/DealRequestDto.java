package developing.springboot.currencyexchangeboothapp.dto;

import developing.springboot.currencyexchangeboothapp.lib.ValidPhoneNumber;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DealRequestDto {
    private String ccySale;
    private String ccyBuy;
    @Min(1)
    private BigDecimal ccySaleAmount;
    @ValidPhoneNumber
    private String phone;
}
