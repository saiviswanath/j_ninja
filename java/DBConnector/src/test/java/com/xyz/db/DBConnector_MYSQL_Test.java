package com.xyz.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hamcrest.core.IsEqual;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

/**
 * Ref: https://blogs.oracle.com/randystuph/entry/injecting_jndi_datasources_for_junit
 * 
 * @author viswa
 *
 */
public class DBConnector_MYSQL_Test {
  private static InitialContext context;
  private static MysqlConnectionPoolDataSource ds;
  private static String dsName;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

    System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        "org.apache.naming.java.javaURLContextFactory");
    System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
    context = new InitialContext();

    ds = new MysqlConnectionPoolDataSource();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document document = builder.parse(new FileInputStream(createContextFile()));
    Node resourceNode = document.getElementsByTagName("Resource").item(0);
    NamedNodeMap resourceAttrs = resourceNode.getAttributes();

    ds.setUser(resourceAttrs.getNamedItem("username").getNodeValue());
    ds.setPassword(resourceAttrs.getNamedItem("password").getNodeValue());
    ds.setURL(resourceAttrs.getNamedItem("url").getNodeValue());
    dsName = resourceAttrs.getNamedItem("name").getNodeValue();

    context.createSubcontext("java:");
    context.createSubcontext("java:comp");
    context.createSubcontext("java:comp/env");
    context.createSubcontext("java:comp/env/jdbc");

    context.bind("java:comp/env/" + dsName, ds);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Test
  public void testGetDBConnection() {
    // Test if we are able to look up JNDI for DataSource object and
    // get back successful connection
    Connection con = DBConnector.getDBConnection();
    assertNotNull(con);

    try (PreparedStatement stmt = con.prepareStatement("select 1")) {
      ResultSet rs = stmt.executeQuery();
      assertNotNull(rs);
      assertTrue(rs.next());
      assertThat("1", IsEqual.equalTo(rs.getString(1)));
      assertFalse(rs.next());
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    }

    // Second Connection
    Connection con1 = DBConnector.getDBConnection();
    assertNotNull(con1);

    // Clean and do a fresh JNDI lookup
    DBConnector.clean();
    Connection con2 = DBConnector.getDBConnection();
    assertNotNull(con2);
  }

  @Test
  public void testGetDBConnectionOnUnbind() {
    DBConnector.clean(); // For fresh JNDI lookup
    try {
      context.unbind("java:comp/env/" + dsName);
    } catch (NamingException e) {
      throw new RuntimeException(e.getMessage());
    }
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("Unable to connect to DB");
    DBConnector.getDBConnection();
  }

  @Test
  public void testGetDBConnectionOnNullDataSource() {
    DBConnector.clean(); // For fresh JNDI lookup
    ds = null;
    try {
      context.bind("java:comp/env/" + dsName, ds); // Unbound in testGetDBConnectionOnUnbind, so
      // rebinding same jndi name to null
    } catch (NamingException e) {
      throw new RuntimeException(e.getMessage());
    }

    thrown.expect(RuntimeException.class);
    thrown.expectMessage("Unable to connect to DB");
    DBConnector.getDBConnection();
  }
  
  @Test
  public void testGetDataSource() {
    DataSource dataSource = DBConnector.getDataSource();
    assertNotNull(dataSource);
  }

  private static String createContextFile() {
    File ctxtFile = null;
    try (FileOutputStream fos =
        new FileOutputStream(ctxtFile = File.createTempFile("context", ".xml"))) {
      if (!ctxtFile.canWrite()) {
        throw new RuntimeException("Could not write to " + ctxtFile.getAbsolutePath());
      }
      ctxtFile.deleteOnExit();

      StringBuilder sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      sb.append("<Context>");
      sb.append("<Resource name=\"jdbc/collegeDB\" ");
      sb.append("auth=\"Container\" ");
      sb.append("type=\"javax.sql.DataSource\" ");
      sb.append("username=\"collegeDBUser\" ");
      sb.append("password=\"foobar\" ");
      sb.append("driverClassName=\"com.mysql.jdbc.Driver\" ");
      sb.append("url=\"jdbc:mysql://localhost:3306/collegeDB\" ");
      sb.append("validationQuery=\"SELECT 1\" ");
      sb.append("testOnBorrow=\"true\" ");
      sb.append("maxActive=\"10\" ");
      sb.append("maxIdle=\"5\"/>");
      sb.append("</Context>");

      fos.write(sb.toString().getBytes());

    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    return ctxtFile.getAbsolutePath();
  }
}
