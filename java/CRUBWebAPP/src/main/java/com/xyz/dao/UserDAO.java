package com.xyz.dao;

import java.util.List;

import com.xyz.dto.UpdatePasswordBean;
import com.xyz.dto.User;

public interface UserDAO {
  public List<User> findAllActiveUsers();
  public List<String> findRolesByUserId(int userId);
  public List<String> findAllRoles();
  public void createUser(User user);
  public List<Integer> findRoleIdsByRoleNames(List<String> roles);
  public int findUserIdByName(String userName);
  public User findByName(String userName);
  public void updateUser(User user);
  public boolean deteteUserByName(String userName);
  public boolean validPassword(String uName, String oldPassword);
  public void updatePassword(UpdatePasswordBean updatePwdBean);
  public void resetPassword(UpdatePasswordBean pwdBean);
}
