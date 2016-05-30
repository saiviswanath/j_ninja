package com.xyz.validators;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateNotEmptyValidator implements
ConstraintValidator<DateNotEmpty, Date> {

	@Override
	public void initialize(DateNotEmpty annotation) {
	}

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext ctxt) {
		if (date == null) {
			return false;
		}
		return true;
	}

}
