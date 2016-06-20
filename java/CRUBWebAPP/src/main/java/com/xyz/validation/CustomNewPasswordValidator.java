package com.xyz.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.xyz.form.beans.PasswordResetBean;

@Component
public class CustomNewPasswordValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return PasswordResetBean.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    PasswordResetBean pwdResetBean = (PasswordResetBean) target;
    if (!pwdResetBean.getPassword().equals(pwdResetBean.getConfirmPassword())) {
      errors.rejectValue("password", "user.password.missMatch");
    }
  }

}
