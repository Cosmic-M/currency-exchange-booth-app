package developing.springboot.currencyexchangeboothapp.dto;

import developing.springboot.currencyexchangeboothapp.lib.ValidPhoneNumber;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DealRequestDto {
    @Column(length = 3)
    private String ccySale;
    @Column(length = 3)
    private String ccyBuy;
    @Min(1)
    private BigDecimal ccySaleAmount;
    @ValidPhoneNumber
    private String phone;
}
