/**
 * 
 */
package com.xyz.hrapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xyz.hrapp.beans.EmployeeBean;
import com.xyz.hrapp.db.DataHandler;

/**
 * @author viswa
 *
 */
public class HRAppDaoImpl implements HRAppDao {
  private static final Logger logger = Logger.getLogger(HRAppDaoImpl.class);

  /**
   * Returns all employee details
   * @throws SQLException
   */
  @Override
  public Set<EmployeeBean> getAllEmployees() throws SQLException {
    Connection con = DataHandler.getDBConnection();
    String sql = "select employee_id, first_name, last_name, email, phone_number, hire_date"
        + " from employees order by employee_id";
    logger.debug("Executing sql: " + sql);
    Set<EmployeeBean> employees = null;
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        employees = new HashSet<EmployeeBean>();
        while (rs.next()) {
          EmployeeBean employee = new EmployeeBean();
          employee.setId(rs.getInt(1));
          employee.setFirstName(rs.getString(2));
          employee.setLastName(rs.getString(3));
          employee.setEmail(rs.getString(4));
          employee.setPhoneNumber(rs.getString(5));
          employee.setHireDate(rs.getDate(6));
          employees.add(employee);
        }
      }
    }
    return employees;
  }

  /**
   * Return employee details by firstname
   * @param firstName
   * throws SQLException
   */
  @Override
  public EmployeeBean getEmployeeByFirstName(String firstName) throws SQLException {
    Connection con = DataHandler.getDBConnection();
    String sql = "select employee_id, first_name, last_name, email, phone_number, hire_date"
        + " from employees where first_name = ?";
    logger.debug("Executing sql: " + sql);
    EmployeeBean employee = null;
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setString(1, firstName);
      ResultSet rs = stmt.executeQuery();
      if (rs != null) {
        while(rs.next()) {
          employee = new EmployeeBean();
          employee.setId(rs.getInt(1));
          employee.setFirstName(rs.getString(2));
          employee.setLastName(rs.getString(3));
          employee.setEmail(rs.getString(4));
          employee.setPhoneNumber(rs.getString(5));
          employee.setHireDate(rs.getDate(6));
          break;
        }
      }
    }
    return employee;
  }


}
