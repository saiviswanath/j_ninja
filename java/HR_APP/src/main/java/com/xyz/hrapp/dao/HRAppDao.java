package com.xyz.hrapp.dao;

import java.sql.SQLException;
import java.util.Set;

import com.xyz.hrapp.beans.EmployeeBean;

public interface HRAppDao {
  Set<EmployeeBean> getAllEmployees() throws SQLException;
  EmployeeBean getEmployeeByFirstName(String firstName) throws SQLException;
}
