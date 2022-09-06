package developing.springboot.currencyexchangeboothapp.service.mapper;

import developing.springboot.currencyexchangeboothapp.dto.PasswordRequestDto;
import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OtpPasswordMapperTest {
    @InjectMocks
    private OtpPasswordMapper otpPasswordMapper;
    private PasswordRequestDto passwordRequestDto;

    @BeforeEach
    void setUp() {
        OtpPassword otpPassword = new OtpPassword();
        otpPassword.setId(10L);
        otpPassword.setPassword("123456");

        passwordRequestDto = new PasswordRequestDto();
        passwordRequestDto.setId(10L);
        passwordRequestDto.setPassword("123456");
    }

    @Test
    void mapToModel_ok() {
        OtpPassword result = otpPasswordMapper.toModel(passwordRequestDto);
        Assertions.assertEquals(10L, result.getId());
        Assertions.assertEquals("123456", result.getPassword());
    }
}
