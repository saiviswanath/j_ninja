package com.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnectionManager {
  public static DataSource datasource;

  private DBConnectionManager() {}

  public static synchronized Connection getConnection() {
    if (datasource != null){
      try {
        return datasource.getConnection();
      } catch (SQLException e) {              
        throw new RuntimeException("No DB") ;
      }
    }
    else{       
      try {
        Context context = new InitialContext();
        datasource = (DataSource)context.lookup("java:comp/env/jdbc/test");

        if (datasource == null ) { 
          throw new Exception("No DB"); 
        }               
        return datasource.getConnection();                
      } catch (Exception e) {
        throw new RuntimeException("No DB") ;
      }
    }
  }

  public static void clean(){
    datasource = null;
  }

}
