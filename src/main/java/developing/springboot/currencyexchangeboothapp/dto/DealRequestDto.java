package developing.springboot.currencyexchangeboothapp.dto;

import java.math.BigDecimal;

import developing.springboot.currencyexchangeboothapp.lib.ValidPhoneNumber;
import lombok.*;

import javax.validation.constraints.Min;

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
