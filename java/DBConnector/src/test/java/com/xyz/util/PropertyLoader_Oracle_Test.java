package com.xyz.util;

import static org.junit.Assert.*;

import java.util.Properties;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PropertyLoader_Oracle_Test {

  public void testGetProperties() {
    Properties props = PropertyLoader.getProperties();
    assertNotNull(props);
    String dsProp = props.getProperty(PropertyConstants.JNDI_DATASOURCE_NAME);
    assertNotNull(dsProp);
    assertThat("java:comp/env/jdbc/XE", IsEqual.equalTo(dsProp));
  }

}
