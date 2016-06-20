package com.xyz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xyz.db.DBConnector;
import com.xyz.dto.UpdatePasswordBean;
import com.xyz.dto.User;

@Repository
public class UserDAOImpl implements UserDAO {
  private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

  @Override
  public List<User> findAllActiveUsers() {
    List<User> userList = new ArrayList<>();
    String sql = "select userid, username, password, email, enabled from User where enabled=1";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          User user = new User();
          user.setUserName(rs.getString(2));
          user.setPassword(rs.getString(3));
          user.setEmail(rs.getString(4));
          user.setEnabled(rs.getInt(5));
          user.setRoles(findRolesByUserId(rs.getInt(1)));
          userList.add(user);
        }
      }
    } catch (SQLException e) {
      logger.error("Error listing all users", e);
    }
    return userList;
  }

  @Override
  public List<String> findRolesByUserId(int userId) {
    List<String> roleList = new ArrayList<>();
    String sql =
        "select r.rolename from User u inner join user_role_map urm on u.userid=urm.userid inner join "
            + "Role r on urm.roleid=r.roleid and u.userid=?";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          roleList.add(rs.getString(1));
        }
      }
    } catch (SQLException e) {
      logger.error("Error executing sql " + sql, e);
    }
    return roleList;
  }

  @Override
  public List<String> findAllRoles() {
    List<String> roleList = new ArrayList<>();
    String sql = "select rolename from role";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          roleList.add(rs.getString(1));
        }
      }
    } catch (SQLException e) {
      logger.error("Error executing sql " + sql, e);
    }
    return roleList;
  }

  @Override
  public void createUser(User user) {
    Connection connection = DBConnector.getDBConnection();
    try {
      connection.setAutoCommit(false);
      String userInsertSql =
          "insert into User(UserName, Password, email, enabled) " + "values(?, ?, ?, ?)";
      PreparedStatement userInsertStmt = connection.prepareStatement(userInsertSql);
      userInsertStmt.setString(1, user.getUserName());
      userInsertStmt.setString(2, user.getPassword());
      userInsertStmt.setString(3, user.getEmail());
      userInsertStmt.setInt(4, user.getEnabled());
      userInsertStmt.executeUpdate();

      String retrieveUserIdSql = "select max(userId) from User";
      PreparedStatement retrieveUserIdStmt = connection.prepareStatement(retrieveUserIdSql);
      ResultSet rs = retrieveUserIdStmt.executeQuery();
      int userId = 0;
      if (rs != null) {
        while (rs.next()) {
          userId = rs.getInt(1);
        }
      }

      insertIntoUserRoleMappingWithUserIdAndRoles(connection, userId, user.getRoles());

      connection.commit();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException e1) {
        logger.error("Error rolling back transaction", e);
      }
      logger.error("Error inserting user record", e);
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        logger.error("Error closing DB connection", e);
      }
    }


  }

  private void insertIntoUserRoleMappingWithUserIdAndRoles(Connection connection, int userId,
      List<String> roles) throws SQLException {
    List<Integer> roleIds = findRoleIdsByRoleNames(roles);
    String insertUserRoleMappingSql = "insert into user_role_map(UserId, " + "RoleId) values(?, ?)";
    PreparedStatement insertUserRoleMappingStmt =
        connection.prepareStatement(insertUserRoleMappingSql);
    for (Integer roleId : roleIds) {
      insertUserRoleMappingStmt.setInt(1, userId);
      insertUserRoleMappingStmt.setInt(2, roleId);
      insertUserRoleMappingStmt.executeUpdate();
    }
  }

  public List<Integer> findRoleIdsByRoleNames(List<String> roles) {
    List<Integer> roleIdList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < roles.size(); i++) {
      if (sb.length() == 0) {
        sb.append("?");
      } else {
        sb.append(", ?");
      }
    }
    String sql = "select RoleId from Role where RoleName in (" + sb.toString() + ")";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      int i = 0;
      for (String role : roles) {
        stmt.setString(++i, role);
      }

      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          roleIdList.add(rs.getInt(1));
        }
      }
    } catch (SQLException e) {
      logger.error("Error executing sql " + sql, e);
    }
    return roleIdList;
  }

  @Override
  public int findUserIdByName(String userName) {
    String retrieveUserIdSql = "select userId from User where userName=?";
    int userId = 0;
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement retrieveUserIdStmt = connection.prepareStatement(retrieveUserIdSql);
      retrieveUserIdStmt.setString(1, userName);
      ResultSet rs = retrieveUserIdStmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          userId = rs.getInt(1);
          break; // Treating userName as unique record
        }
      }
    } catch (SQLException e) {
      logger.error("Error executing sql" + retrieveUserIdSql, e);
    }
    return userId;
  }

  @Override
  public User findByName(String userName) {
    String sql = "select userId, password, email, enabled from User " + " where userName=?";
    User user = null;
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, userName);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          user = new User();
          user.setUserName(userName);
          user.setPassword(rs.getString(2));
          user.setEmail(rs.getString(3));
          user.setEnabled(rs.getInt(4));
          user.setRoles(findRolesByUserId(rs.getInt(1)));
          break; // Treating username as unique record
        }
      }
    } catch (SQLException e) {
      logger.error("Error executing sql " + sql, e);
    }
    return user;
  }

  @Override
  public void updateUser(User user) {
    Connection connection = null;
    String sql = "update User set userName=?, email=?, enabled=? where userName=?";
    try {
      connection = DBConnector.getDBConnection();
      connection.setAutoCommit(false);
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, user.getUserName());
      stmt.setString(2, user.getEmail());
      stmt.setInt(3, user.getEnabled());
      stmt.setString(4, user.getUserName());
      stmt.executeUpdate();

      int userId = findUserIdByName(user.getUserName());

      List<String> currentRoles = user.getRoles();
      List<String> rolesInDB = findRolesByUserId(userId);
      // ABC, AB - Add C
      // AB, ABC - Delete C
      // AB, AB - No Op

      List<String> rolesToAdd = new ArrayList<>();
      List<String> rolesToDelete = new ArrayList<>();
      for (String s : currentRoles) {
        if (rolesInDB.contains(s)) {
          continue;
        } else {
          rolesToAdd.add(s);
        }
      }

      for (String s : rolesInDB) {
        if (currentRoles.contains(s)) {
          continue;
        } else {
          rolesToDelete.add(s);
        }
      }

      if (rolesToAdd.size() > 0) {
        insertIntoUserRoleMappingWithUserIdAndRoles(connection, userId, rolesToAdd);
      }

      if (rolesToDelete.size() > 0) {
        deleteFromUserRoleMappingWithUserIdAndRoles(connection, userId, rolesToDelete);
      }

      connection.commit();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException e1) {
        logger.error("Error rolling back transaction", e);
      }
      logger.error("Error updating student", e);
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        logger.error("Error closing connection", e);
      }
    }
  }

  private void deleteFromUserRoleMappingWithUserIdAndRoles(Connection connection, int userId,
      List<String> roles) throws SQLException {
    List<Integer> roleIds = findRoleIdsByRoleNames(roles);
    String deleteUseRoleMappingSql = "delete from user_role_map where userId=? and roleId=?";
    PreparedStatement deleteUserRoleMappingStmt =
        connection.prepareStatement(deleteUseRoleMappingSql);
    for (Integer roleId : roleIds) {
      deleteUserRoleMappingStmt.setInt(1, userId);
      deleteUserRoleMappingStmt.setInt(2, roleId);
      deleteUserRoleMappingStmt.executeUpdate();
    }

  }

  @Override
  public boolean deteteUserByName(String userName) {
    Connection connection = null;
    try {
      connection = DBConnector.getDBConnection();
      connection.setAutoCommit(false);

      int userId = findUserIdByName(userName);
      if (userId == 0) {
        return false;
      }
      List<String> roles = findRolesByUserId(userId);
      deleteFromUserRoleMappingWithUserIdAndRoles(connection, userId, roles);

      String sql = "delete from User where userId=?";
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setInt(1, userId);
      stmt.executeUpdate();

      connection.commit();
    } catch (SQLException e) {
      logger.error("Error deleting user record", e);
      try {
        connection.rollback();
      } catch (SQLException e1) {
        logger.error("Error rolling back transaction", e);
      }
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        logger.error("Error closing connection", e);
      }
    }
    return true;
  }

  @Override
  public boolean validPassword(String uName, String oldPassword) {
    String sql = "select password from User where userName=?";
    String dbPwd = null;
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, uName);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          dbPwd = rs.getString(1);
        }
      }

      if (dbPwd != null && dbPwd.equals(oldPassword)) {
        return true;
      }
    } catch (SQLException e) {
      logger.error("Error validating password", e);
    }
    return false;
  }

  @Override
  public void updatePassword(UpdatePasswordBean updatePwdBean) {
    String sql = "update User set password = ? where userName = ?";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, updatePwdBean.getPassword());
      stmt.setString(2, updatePwdBean.getUserName());
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("Couldn't update Password", e);
    }
  }

  @Override
  public void resetPassword(UpdatePasswordBean pwdBean) {
    String sql = "update User set password = ? where userName = ?";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, pwdBean.getPassword());
      stmt.setString(2, pwdBean.getUserName());
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("Error updating password", e);
    }
  }
}
