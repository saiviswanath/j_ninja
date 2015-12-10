package com.xyz.java.dao;

import java.sql.SQLException;
import java.util.Set;

import com.xyz.beans.java.EmployeeBean;


public interface EmployeeDAO {
  Set<EmployeeBean> getAllEmployees() throws SQLException;
}
