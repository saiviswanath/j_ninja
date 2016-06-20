package com.xyz.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.xyz.form.beans.User;

@Component
public class CustomUserValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;
    String password = user.getPassword();
    String retypedPassword = user.getRetypedPassword();
    if (!password.equals(retypedPassword)) {
      errors.rejectValue("password", "user.password.missMatch");
    }
  }

}
