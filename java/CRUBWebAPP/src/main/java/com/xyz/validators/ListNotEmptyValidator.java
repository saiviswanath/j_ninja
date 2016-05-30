package com.xyz.validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListNotEmptyValidator implements
		ConstraintValidator<ListNotEmpty, List<String>> {

	@Override
	public void initialize(ListNotEmpty arg0) {
	}

	@Override
	public boolean isValid(List<String> list, ConstraintValidatorContext ctxt) {
		if (list == null || list.size() < 1) {
			return false;
		}
		return true;
	}

}
