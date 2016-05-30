package com.xyz.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { LongNotEmptyValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LongNotEmpty {
	String message() default "Cannot be Empty";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
