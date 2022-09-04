package developing.springboot.currencyexchangeboothapp.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidRequestDto {
    String ccySale;
    String ccyBuy;
    BigDecimal ccySaleAmount;
    String phone;
}
