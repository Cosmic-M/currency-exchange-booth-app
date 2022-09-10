package developing.springboot.currencyexchangeboothapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PasswordRequestDto {
    private Long id;
    private String password;
}
