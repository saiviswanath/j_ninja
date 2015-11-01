package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.template.EntryMapper;
import org.apache.directory.ldap.client.template.LdapConnectionTemplate;

import com.db.DBConnectionManager;
import com.model.RoleMap;

public class Authorizer {
  private LdapConnectionTemplate template;
  private boolean authorizedUser;
  
/*  private static ThreadLocal<Authorizer> instance = new ThreadLocal<Authorizer>() {
    @Override
    protected Authorizer initialValue() {
      return new Authorizer();
    }
  };

  public static synchronized Authorizer getInstance() {
    return instance.get();
  }*/

  private Set<String> getLDAPRoles(String uid) {
    // Add anonymous role? 
    // Sample Lookup considering a role attribute in LDAP
    // for uid comma-separated.
    Set<String> roles = new HashSet<String>();
    String roleString = template.lookup(
        template.newDn( "uid=" + uid +",ou=people,dc=maxcrc,dc=com" ),
        null,
        new EntryMapper<String>() {
          @Override
          public String map( Entry entry ) throws LdapException {
            return entry.get( "sn" ).getString(); // Treat sn as roles
          }
        } );
    String[] str = roleString.split(",");
    for (String s : str) {
      roles.add(s);
    }
    return roles;
  }

  private Set<String> getMappedRolesInDB(String path, Set<String> roles) {
    Connection con = DBConnectionManager.getConnection();
    String sql =
        "select role.roleName from Roles role inner join Links link on role.roleId=link.roleIdFK and role.roleName in ("
            + this.getRoleString(roles) + ") and link.url='" + path + "'";
    if (con != null) {
      try {
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs != null) {
          Set<String> roleSet = new HashSet<String>();
          while(rs.next()){
            roleSet.add(rs.getString(1));
          }
          if (roleSet.isEmpty()) {
            return null;
          } else {
            return roleSet;
          }
        }
      } catch (SQLException e) {
      } finally {
        try {
          con.close();
        } catch (SQLException e) {
        }
      }
    }
    return null;
  }

  private StringBuilder getRoleString(Set<String> roles) {
    StringBuilder roleString = new StringBuilder();
    for (String role : roles) {
      if (roleString.length() == 0) {
        roleString.append("'" + role + "'");
      } else {
        roleString.append("," + "'" + role + "'");
      }
    }
    return roleString;
  }

  public RoleMap buildMap(String uid, String path) {
    RoleMap roleMap = new RoleMap();
    Set<String> roles = this.getLDAPRoles(uid);
    if (roles.isEmpty()) {
      this.setAuthorizedUser(false);
      return null; // Empty
    }
    Set<String> mappedRoles = getMappedRolesInDB(path, roles);
    if (mappedRoles != null) {
      this.setAuthorizedUser(true);
    }

    // Build map for mapping/non-mapping role-path
    for (String role : roles) {
      if (mappedRoles.contains(role)) {
        roleMap.put(role, path);
      } else {
        roleMap.put(role);
      }
    }
    return roleMap;
  }

  public RoleMap rebuildMap(String uid, String path, RoleMap existingMap) {
    // Roles will not be empty
/*    if (existingMap == null) {
      return this.buildMap(uid, path);
      
    }*/
    Set<String> roles = existingMap.getRoles();
    for (String role : roles) {
      Set<String> links = existingMap.getLinksForRole(role);
      if (links.contains(path)) {
        // There is a role for user with a link, so authrorized.
        this.setAuthorizedUser(true);
        return existingMap; // Unmodified map
      }
    }
    Set<String> mappedRoles = getMappedRolesInDB(path, roles);
    if (mappedRoles != null) {
      this.setAuthorizedUser(true);
    }

    // Roles already built into map, just add the new links to mapped roles
    for (String role : mappedRoles) {
      existingMap.put(role, path);
    }
    return existingMap;
  }

  public boolean isAuthorizedUser() {
    return this.authorizedUser;
  }

  public void setAuthorizedUser(boolean authorizedUser) {
    this.authorizedUser = authorizedUser;
  }

  public void setLDAPTemplate(LdapConnectionTemplate template) {
    this.template = template;
  }
}
