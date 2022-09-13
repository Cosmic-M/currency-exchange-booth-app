package developing.springboot.currencyexchangeboothapp.dto;

import javax.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PasswordRequestDto {
    @Column(length = 6)
    private String password;
}
