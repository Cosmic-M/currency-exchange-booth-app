package developing.springboot.currencyexchangeboothapp.dto;

import developing.springboot.currencyexchangeboothapp.model.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidResponseDto {
    Long id;
    String ccySale;
    String ccyBuy;
    BigDecimal ccySaleAmount;
    BigDecimal ccyBuyAmount;
    String phone;
    LocalDateTime dateTime;
    Status status;
}
