package com.xyz.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.xyz.dao.UserDAO;
import com.xyz.form.beans.UpdatePasswordBean;
import com.xyz.util.AuthUtils;

@Component
public class CustomPasswordValidator implements Validator {
  @Autowired
  private UserDAO userDao;

  @Override
  public boolean supports(Class<?> clazz) {
    return UpdatePasswordBean.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UpdatePasswordBean pwdBean = (UpdatePasswordBean) target;
    String oldPassword = pwdBean.getOldPassword();
    String newPassword = pwdBean.getNewPassword();
    String retypedPassword = pwdBean.getCfNewPassword();

    String uName = AuthUtils.getCurrentUser();

    if (uName != null) {
      boolean validPwd = userDao.validPassword(uName, oldPassword);
      if (!validPwd) {
        errors.rejectValue("oldPassword", "user.password.incorrect");
      }
    } else {
      errors.reject("newPassword", "user.password.cannotvalidate");
    }

    if (!newPassword.equals(retypedPassword)) {
      errors.rejectValue("newPassword", "user.password.missMatch");
    }
  }

}
