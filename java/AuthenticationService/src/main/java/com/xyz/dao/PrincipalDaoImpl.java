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
import com.xyz.dto.PrincipalDto;

@Repository
public class PrincipalDaoImpl implements PrincipalDao {
  private static final Logger logger = Logger.getLogger(PrincipalDaoImpl.class);

  @Override
  public PrincipalDto findPrincipal(String userName) {
    String sql = "select username, password, email, enabled from User where username=?";
    PrincipalDto pDto = null;
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, userName);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          pDto = new PrincipalDto();
          pDto.setUsername(rs.getString(1));
          pDto.setPassword(rs.getString(2));
          pDto.setEmail(rs.getString(3));
          pDto.setEnabled(rs.getInt(4));
          pDto.setRoles(findRolesByName(userName));
          break; // Unique by username
        }
      }
    } catch (SQLException e) {
      logger.error("Error fetching pricipal details", e);
    }
    return pDto;
  }

  @Override
  public List<String> findRolesByName(String userName) {
    List<String> roleList = new ArrayList<>();
    String sql =
        "select r.rolename from User u inner join user_role_map urm on u.userid=urm.userid inner join "
            + "Role r on urm.roleid=r.roleid and u.username=?";
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, userName);
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
  public boolean usernameExists(String username) {
    String sql = "select * from User where username=?";
    boolean exists = false;
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, username);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          exists = true;
          break; // Unique by username
        }
      }
    } catch (SQLException e) {
      logger.error("Error fetching pricipal details", e);
    }
    return exists;
  }

}
