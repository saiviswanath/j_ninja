package com.xyz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xyz.dao.UserDAO;
import com.xyz.form.beans.ForGotPasswordBean;
import com.xyz.form.beans.PasswordResetBean;
import com.xyz.form.beans.UpdatePasswordBean;
import com.xyz.util.AuthUtils;
import com.xyz.util.UserEmailUtil;
import com.xyz.validation.CustomForGotPasswordValidator;
import com.xyz.validation.CustomNewPasswordValidator;
import com.xyz.validation.CustomPasswordValidator;

@Controller
public class LoginController {
  @Autowired
  private CustomPasswordValidator customPwdValidator;
  @Autowired
  private CustomForGotPasswordValidator customForgotPwdValidator;
  @Autowired
  private UserDAO userDao;
  @Autowired
  private UserEmailUtil userEmailUtil;
  @Autowired
  private CustomNewPasswordValidator customNewPwdValidator;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login() {
    return new ModelAndView("parentLogin");
  }

  @RequestMapping(value = "/loginwithmessage", method = RequestMethod.GET)
  public ModelAndView loginwithmessage() {
    ModelAndView mav = new ModelAndView("loginwithmessage");
    mav.addObject("DisplayMessage", " Invalid UserName/Password");
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @RequestMapping(value = "/403", method = RequestMethod.GET)
  public ModelAndView handler403() {
    ModelAndView mav = new ModelAndView("403");
    String user = AuthUtils.getCurrentUser();
    if (user != null) {
      mav.addObject("username", user);
    } else {
      mav.addObject("username", "Anonymous");
    }
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @RequestMapping(value = "/passwordChangeForm.do", method = RequestMethod.GET)
  public ModelAndView passwordChangeForm() {
    return new ModelAndView("passwordChangeForm", "updatePasswordBean", new UpdatePasswordBean());
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
  @RequestMapping(value = "/updatePassword.do", method = RequestMethod.PUT)
  public ModelAndView updatePassword(
      @Valid @ModelAttribute("updatePasswordBean") UpdatePasswordBean updatePasswordBean,
      BindingResult bindingResult) {
    customPwdValidator.validate(updatePasswordBean, bindingResult);
    if (bindingResult.hasErrors()) {
      return new ModelAndView("passwordChangeForm");
    }
    com.xyz.dto.UpdatePasswordBean updatePwdBean = new com.xyz.dto.UpdatePasswordBean();
    updatePwdBean.setUserName(AuthUtils.getCurrentUser());
    updatePwdBean.setPassword(updatePasswordBean.getCfNewPassword());
    userDao.updatePassword(updatePwdBean);

    ModelAndView mav = new ModelAndView("passwordChangeSuccess");
    return mav;
  }

  @RequestMapping(value = "/forgotPasswordPage", method = RequestMethod.GET)
  public ModelAndView forgotPasswordPage() {
    return new ModelAndView("forgotPasswordPage", "forGotPasswordBean", new ForGotPasswordBean());
  }

  @RequestMapping(value = "/forGotPassword", method = RequestMethod.GET)
  public ModelAndView forGotPassword(
      @Valid @ModelAttribute("forGotPasswordBean") ForGotPasswordBean forGotPwdBean,
      BindingResult bindingResult) {
    customForgotPwdValidator.validate(forGotPwdBean, bindingResult);
    if (bindingResult.hasErrors()) {
      return new ModelAndView("forgotPasswordPage");
    }
    userEmailUtil.sendPasswordResetEmail(forGotPwdBean);
    ModelAndView mav = new ModelAndView("forGotPasswordSuccessPage");
    return mav;
  }

  @RequestMapping(value = "/passwordResetForm", method = RequestMethod.GET)
  public ModelAndView passwordResetForm(@RequestParam("user") String user,
      final HttpServletRequest req) {
    ModelAndView mav =
        new ModelAndView("passwordResetForm", "passwordResetBean", new PasswordResetBean());
    HttpSession session = req.getSession();
    session.setAttribute("sessionuser", user);
    return mav;
  }

  @RequestMapping(value = "/passwordReset", method = RequestMethod.PUT)
  public ModelAndView passwordReset(
      @Valid @ModelAttribute("passwordResetBean") PasswordResetBean passwordResetBean,
      BindingResult bindingResult, final HttpServletRequest req) {
    customNewPwdValidator.validate(passwordResetBean, bindingResult);
    if (bindingResult.hasErrors()) {
      return new ModelAndView("passwordResetForm");
    }

    HttpSession session = req.getSession();
    String sessionUser = (String) session.getAttribute("sessionuser");
    com.xyz.dto.UpdatePasswordBean pwdBean = new com.xyz.dto.UpdatePasswordBean();
    pwdBean.setUserName(sessionUser);
    pwdBean.setPassword(passwordResetBean.getConfirmPassword());
    userDao.resetPassword(pwdBean);
    ModelAndView mav = new ModelAndView("passwordResetSuccess");
    return mav;
  }
}
