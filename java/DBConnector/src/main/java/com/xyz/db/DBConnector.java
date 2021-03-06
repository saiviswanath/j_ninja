/**
 * 
 */
package com.xyz.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.xyz.util.PropertyConstants;
import com.xyz.util.PropertyLoader;

/**
 * @author viswa
 *
 */
public class DBConnector {
  private static final Logger logger = Logger.getLogger(DBConnector.class);
  private static DataSource dataSource;
  private static final Properties props = PropertyLoader.getProperties();

  private DBConnector() {}

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
        dataSource =
            (DataSource) context.lookup(props.getProperty(PropertyConstants.JNDI_DATASOURCE_NAME));
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

  public static synchronized DataSource getDataSource() {
    if (dataSource == null) {
      Context context;
      try {
        context = new InitialContext();
        dataSource =
            (DataSource) context.lookup(props.getProperty(PropertyConstants.JNDI_DATASOURCE_NAME));
      } catch (NamingException e) {
        logger.error("Data Source not found");
        throw new RuntimeException("Data Source not found");
      }
    }
    return dataSource;
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
