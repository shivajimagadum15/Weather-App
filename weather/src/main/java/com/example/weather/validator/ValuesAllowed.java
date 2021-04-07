package com.example.weather.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ZipCodeValidator.class})
public @interface ValuesAllowed {
    String message() default "Invalid zip code.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
