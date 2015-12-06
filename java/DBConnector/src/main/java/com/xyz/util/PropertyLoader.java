package com.xyz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyLoader {
  private static final Logger logger = Logger.getLogger(PropertyLoader.class);

  private PropertyLoader() {}

  public static Properties getProperties() throws RuntimeException {
    InputStream in =
        PropertyLoader.class.getClassLoader().getResourceAsStream("Application.properties");
    if (in == null) {
      logger.error("Application.properties not defined");
      throw new RuntimeException("Application.properties not defined");
    }
    Properties props = new Properties();
    try {
      props.load(in);
    } catch (IOException e) {
      logger.error("Could not load Application.properties");
      throw new RuntimeException("Could not load Application.properties");
    }
    return props;
  }
}
