package com.xyz.autheticationservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.xyz.authenticationservice.dto.PrincipalDto;
import com.xyz.db.DBConnector;

@Repository
public class PrincipalDaoImpl implements PrincipalDao {
  private static final Logger logger = Logger.getLogger(PrincipalDaoImpl.class);
  private static final Integer ZERO = new Integer(0);

  @Override
  public PrincipalDto findPrincipal(String userName) {
    String sql =
        "select username, passwordHash, firstname, lastname, email, timezone, "
            + "pwd_reqs_reset, version, deleted from UserDetails where username=?";
    PrincipalDto pDto = null;
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      stmt.setString(1, userName);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          pDto = new PrincipalDto();
          pDto.setUsername(rs.getString(1));
          pDto.setPasswordHash(rs.getString(2));
          pDto.setFirstName(rs.getString(3));
          pDto.setLastName(rs.getString(4));
          pDto.setEmailAddress(rs.getString(5));
          pDto.setPreferredTimeZoneId(rs.getString(6));
          pDto.setPasswordRequiresReset(rs.getBoolean(7));
          pDto.setVersion(rs.getInt(8));
          pDto.setArchived(rs.getBoolean(9));
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
        "select r.rolename from UserDetails u inner join Userdetail_role_map urm on u.id=urm.id inner join "
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
    String sql = "select * from UserDetails where username=?";
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

  @Override
  public List<PrincipalDto> findPrincipals() {
    String sql =
        "select username, passwordHash, firstname, lastname, email, timezone, "
            + "pwd_reqs_reset, version, deleted from UserDetails";
    List<PrincipalDto> pDtoList = new ArrayList<>();
    try (Connection connection = DBConnector.getDBConnection()) {
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          PrincipalDto pDto = new PrincipalDto();
          pDto.setUsername(rs.getString(1));
          pDto.setPasswordHash(rs.getString(2));
          pDto.setFirstName(rs.getString(3));
          pDto.setLastName(rs.getString(4));
          pDto.setEmailAddress(rs.getString(5));
          pDto.setPreferredTimeZoneId(rs.getString(6));
          pDto.setPasswordRequiresReset(rs.getBoolean(7));
          pDto.setVersion(rs.getInt(8));
          pDto.setArchived(rs.getBoolean(9));
          pDto.setRoles(findRolesByName(rs.getString(1)));
          pDtoList.add(pDto);
        }
      }

    } catch (SQLException e) {
      logger.error("Error fetching principals from DB", e);
    }
    return pDtoList;
  }

  @Override
  public void createPrincipal(PrincipalDto principalDto) {
    Connection connection = DBConnector.getDBConnection();
    try {
      connection.setAutoCommit(false);
      String userInsertSql =
          "insert into UserDetails(username, passwordHash, firstname, lastname, "
              + "email, timezone, pwd_reqs_reset, version, deleted) "
              + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement userInsertStmt = connection.prepareStatement(userInsertSql);
      userInsertStmt.setString(1, principalDto.getUsername());
      userInsertStmt.setString(2, principalDto.getPassword());
      userInsertStmt.setString(3, principalDto.getFirstName());
      userInsertStmt.setString(4, principalDto.getLastName());
      userInsertStmt.setString(5, principalDto.getEmailAddress());
      userInsertStmt.setString(6, principalDto.getPreferredTimeZoneId());
      userInsertStmt.setBoolean(7, principalDto.isPasswordRequiresReset());
      userInsertStmt.setInt(8, ZERO);
      userInsertStmt.setBoolean(9, principalDto.isArchived());
      userInsertStmt.executeUpdate();

      String retrieveUserIdSql = "select max(Id) from UserDetails";
      PreparedStatement retrieveUserIdStmt = connection.prepareStatement(retrieveUserIdSql);
      ResultSet rs = retrieveUserIdStmt.executeQuery();
      int id = 0;
      if (rs != null) {
        while (rs.next()) {
          id = rs.getInt(1);
        }
      }

      insertIntoUserRoleMappingWithUserIdAndRoles(connection, id, principalDto.getRoles());

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

  private void insertIntoUserRoleMappingWithUserIdAndRoles(Connection connection, int id,
      List<String> roles) throws SQLException {
    List<Integer> roleIds = findRoleIdsByRoleNames(roles);
    String insertUserRoleMappingSql =
        "insert into Userdetail_role_map(id, " + "roleId) values(?, ?)";
    PreparedStatement insertUserRoleMappingStmt =
        connection.prepareStatement(insertUserRoleMappingSql);
    for (Integer roleId : roleIds) {
      insertUserRoleMappingStmt.setInt(1, id);
      insertUserRoleMappingStmt.setInt(2, roleId);
      insertUserRoleMappingStmt.executeUpdate();
    }
  }

  private List<Integer> findRoleIdsByRoleNames(List<String> roles) {
    List<Integer> roleIdList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < roles.size(); i++) {
      if (sb.length() == 0) {
        sb.append("?");
      } else {
        sb.append(", ?");
      }
    }
    String sql = "select roleId from Role where roleName in (" + sb.toString() + ")";
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

}
