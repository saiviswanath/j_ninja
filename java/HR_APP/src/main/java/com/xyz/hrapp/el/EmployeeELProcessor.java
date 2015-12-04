package com.xyz.hrapp.el;

import java.sql.SQLException;
import java.util.Set;

import com.xyz.hrapp.beans.EmployeeBean;
import com.xyz.hrapp.dao.HRAppDaoImpl;

public class EmployeeELProcessor {
  public static EmployeeBean getEmployeeByFirstName(String firstName) throws SQLException {
    // Make it local
    return new HRAppDaoImpl().getEmployeeByFirstName(firstName);
  }

  public static Set<EmployeeBean> getAllEmployees() throws SQLException {
    // Make it local
    return new HRAppDaoImpl().getAllEmployees();
  }
}
