package com.xyz.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.xyz.beans.java.EmployeeBean;
import com.xyz.db.DBConnector;

public class EmployeeDAOImpl implements EmployeeDAO {
  @Override
  public Set<EmployeeBean> getAllEmployees() throws SQLException {
    Connection con = DBConnector.getDBConnection();
    String sql =
        "select employee_id, first_name, last_name, email, phone_number, hire_date"
            + " from employees order by employee_id";
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
}
