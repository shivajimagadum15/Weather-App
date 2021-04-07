package com.example.weather.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ZipCodeValidator implements ConstraintValidator<ValuesAllowed, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches("^[0-9]*", value);
    }
}
