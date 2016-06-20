package com.xyz.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.xyz.dao.UserDAO;
import com.xyz.dto.User;
import com.xyz.form.beans.ForGotPasswordBean;

@Component
public class CustomForGotPasswordValidator implements Validator {
  @Autowired
  private UserDAO userDao;

  @Override
  public boolean supports(Class<?> clazz) {
    return ForGotPasswordBean.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ForGotPasswordBean fPwdBean = (ForGotPasswordBean) target;
    String uName = fPwdBean.getUserName();
    String email = fPwdBean.getEmail();

    User user = userDao.findByName(uName);
    if (user == null) {
      errors.rejectValue("userName", "username.invalid");
    }

    try {
      String refEmail = user.getEmail();
      if (refEmail == null) {
        errors.rejectValue("email", "email.invalid");
      } else if (!refEmail.equals(email)) {
        errors.rejectValue("email", "email.invalid");
      }
    } catch (NullPointerException e) {
      errors.rejectValue("email", "email.invalid");
    }
  }
}
