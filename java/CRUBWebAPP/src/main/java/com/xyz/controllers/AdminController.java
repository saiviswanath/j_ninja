package com.xyz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xyz.authenticationserviceclient.client.PrincipalClient;
import com.xyz.dao.UserDAO;
import com.xyz.dto.User;
import com.xyz.form.beans.UserUpdateInputBean;
import com.xyz.util.DataConverter;
import com.xyz.validation.CustomUserValidator;

@Controller
public class AdminController {
  @Autowired
  private UserDAO userDao;
  @Autowired
  private DataConverter dataConverter;
  @Autowired
  private CustomUserValidator customUserValidator;
  @Autowired
  private PrincipalClient principalClient;

  @ModelAttribute
  public void addModelAttributes(Model model) {
    List<User> userList = userDao.findAllActiveUsers();
    List<String> roleList = userDao.findAllRoles();
    model.addAttribute("roleList", roleList);
    model.addAttribute("userList", userList);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/getPermissionPage.do", method = RequestMethod.GET)
  public ModelAndView getAllUsers() {
    ModelAndView mav = new ModelAndView("permissionPage");
    return mav;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/newUserPage.do", method = RequestMethod.GET)
  public ModelAndView newUserPage() {
    return new ModelAndView("newUserPage", "user", new com.xyz.form.beans.User());
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/newUserDetails.do", method = RequestMethod.POST)
  public ModelAndView newUserDetails(@Valid @ModelAttribute("user") com.xyz.form.beans.User user,
      BindingResult bindingResult) {
    customUserValidator.validate(user, bindingResult);
    if (bindingResult.hasErrors()) {
      return new ModelAndView("newUserPage");
    }
    principalClient.createPrincipal(dataConverter.userFormBeantoPrincipalConverter(user));
    ModelAndView mav = new ModelAndView("newUserSuccess");
    return mav;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/updateUserInputForm.do", method = RequestMethod.GET)
  public ModelAndView updateUserInputForm() {
    ModelAndView mav =
        new ModelAndView("userUpdateInputForm", "userUpdateInputBean", new UserUpdateInputBean());
    return mav;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/fetchUserUpdateFormDetails.do", method = RequestMethod.GET)
  public ModelAndView fetchUserUpdateFormDetails(
      @Valid @ModelAttribute("userUpdateInputBean") UserUpdateInputBean userUpdateInputBean,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("userUpdateInputForm");
    }

    int userId = userDao.findUserIdByName(userUpdateInputBean.getUserName());

    if (userId == 0) {
      ModelAndView mav = new ModelAndView("userUpdateInputForm");
      mav.addObject("ErrorMessage", "No records Found");
      return mav;
    } else {
      User userDto = userDao.findByName(userUpdateInputBean.getUserName());
      return new ModelAndView("fetchUserUpdateFormDetails", "user",
          dataConverter.userDtoToFormBeanConverter(userDto));
    }
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/updateUserDetails.do", method = RequestMethod.PUT)
  public ModelAndView updateUserDetails(@Valid @ModelAttribute com.xyz.form.beans.User user,
      BindingResult bindingResult, final HttpServletResponse res) {
    if (bindingResult.hasErrors()) {
      ModelAndView mav =
          new ModelAndView("fetchUserUpdateFormDetails", "user",
              dataConverter.userDtoToFormBeanConverter(userDao.findByName(user.getUserName())));
      return mav;
    }
    userDao.updateUser(dataConverter.userFormBeantoDtoConverter(user));
    ModelAndView mav = new ModelAndView("permissionPage");
    res.setHeader("Cache-Control", "no-cache");
    return mav;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/deleteUserInputForm.do", method = RequestMethod.GET)
  public ModelAndView deleteUserInputForm() {
    ModelAndView mav =
        new ModelAndView("deleteUserFormPage", "userUpdateInputBean", new UserUpdateInputBean());
    return mav;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/deleteUserFormDetails.do", method = RequestMethod.DELETE)
  public ModelAndView deleteUserFormDetails(
      @Valid @ModelAttribute("userUpdateInputBean") UserUpdateInputBean userUpdateInputBean,
      BindingResult bindingResult, final HttpServletResponse res) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("deleteUserFormPage");
    }
    boolean deletedRecord = userDao.deteteUserByName(userUpdateInputBean.getUserName());
    if (deletedRecord) {
      res.setHeader("Cache-Control", "no-cache");
      return new ModelAndView("permissionPage");
    } else {
      ModelAndView mav = new ModelAndView("deleteUserFormPage");
      mav.addObject("ErrorMessage", "No records Found");
      return mav;
    }
  }
}
