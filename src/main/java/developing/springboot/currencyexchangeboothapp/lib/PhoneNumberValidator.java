package developing.springboot.currencyexchangeboothapp.lib;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String PHONE_VALIDATION_REGEX = "^\\+\\d{12}$";

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        return field != null && field.matches(PHONE_VALIDATION_REGEX);
    }
}
