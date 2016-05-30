package com.xyz.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongNotEmptyValidator implements
ConstraintValidator<LongNotEmpty, Long> {

	@Override
	public void initialize(LongNotEmpty annotation) {
	}

	@Override
	public boolean isValid(Long longValue, ConstraintValidatorContext ctxt) {
		if (longValue == null || longValue < 1) {
			return false;
		}
		return true;
	}

}
