/**
 * 
 */
package com.xyz.hrapp.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * @author viswa
 *
 */
public class DataHandler {
  private DataHandler() {}
  
  private static DataSource dataSource;
  private static final Logger logger = Logger.getLogger(DataHandler.class);
  
  public static synchronized Connection getDBConnection() {
    if (dataSource != null) {
      try {
        return dataSource.getConnection();
      } catch (SQLException e) {
        logger.error("Unable to connect to DB");
        logger.debug(e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Unable to connect to DB");
      }
    } else {
      Context context;
      try {
        context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:comp/env/jdbc/XE");
        if (dataSource == null) {
          logger.error("Unable to connect to DB");
          throw new RuntimeException("Unable to connect to DB");
        }
        return dataSource.getConnection();
      } catch (NamingException e) {
        logger.error("Unable to connect to DB");
        logger.debug(e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Unable to connect to DB");
      } catch (SQLException e1) {
        logger.error("Unable to connect to DB");
        logger.debug(e1.getMessage());
        e1.printStackTrace();
        throw new RuntimeException("Unable to connect to DB");
      }
    }
  }
  
  public static void clean() {
    dataSource = null;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {

  }

}
