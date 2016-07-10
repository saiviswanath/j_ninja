package com.xyz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
  private PropertyLoader() {}

  public static Properties getProperties() throws RuntimeException {
    InputStream in = PropertyLoader.class.getClassLoader().getResourceAsStream("app.properties");
    if (in == null) {
      throw new RuntimeException("Application.properties not defined");
    }
    Properties props = new Properties();
    try {
      props.load(in);
    } catch (IOException e) {
      throw new RuntimeException("Could not load Application.properties");
    }
    return props;
  }
}
