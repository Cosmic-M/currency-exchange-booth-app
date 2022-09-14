package developing.springboot.currencyexchangeboothapp.dto.request;

import developing.springboot.currencyexchangeboothapp.lib.ValidPhoneNumber;
import javax.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PasswordPhoneRequestDto {
    @Column(length = 6)
    private String password;
    @ValidPhoneNumber
    private String phone;
}
