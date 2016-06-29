package com.xyz.crudserviceclient.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.xyz.crudserviceclient.exceptions.CrudServiceConfigurationException;

public class CrudServiceConfiguration {
  private static final Logger logger = Logger.getLogger(CrudServiceConfiguration.class);

  private Properties properties;

  public CrudServiceConfiguration(Properties properties) {
    this.properties = properties;
  }

  public String getProperty(CrudServiceConfigurationProperty key) {
    logger.debug("Requesting property=" + key);
    String property = null;
    if (properties != null) {
      property = properties.getProperty(key.getPropertyKey());
      if (property == null) {
        if (key.getDefaultValue() != null) {
          return key.getDefaultValue();
        }
        throw new CrudServiceConfigurationException("No definition for property: "
            + key.getPropertyKey());
      }
    } else {
      throw new CrudServiceConfigurationException("No definition for property: "
          + key.getPropertyKey());
    }
    return property;
  }

  public int getPropertyInteger(CrudServiceConfigurationProperty key) {
    return Integer.parseInt(getProperty(key));
  }

  public long getPropertyLong(CrudServiceConfigurationProperty key) {
    return Long.parseLong(getProperty(key));
  }

  public double getPropertyDouble(CrudServiceConfigurationProperty key) {
    return Double.parseDouble(getProperty(key));
  }

  public boolean getPropertyBoolean(CrudServiceConfigurationProperty key) {
    return Boolean.parseBoolean(getProperty(key));
  }

  public String getPropertyHttpUrl(CrudServiceConfigurationProperty hostKey,
      CrudServiceConfigurationProperty portKey, CrudServiceConfigurationProperty contextRootKey) {
    return getUrl("http", hostKey, portKey, contextRootKey);
  }

  public String getPropertyHttpsUrl(CrudServiceConfigurationProperty hostKey,
      CrudServiceConfigurationProperty portKey, CrudServiceConfigurationProperty contextRootKey) {
    return getUrl("https", hostKey, portKey, contextRootKey);
  }

  private String getUrl(String protocol, CrudServiceConfigurationProperty hostKey,
      CrudServiceConfigurationProperty portKey, CrudServiceConfigurationProperty contextRootKey) {
    StringBuilder sb = new StringBuilder();
    sb.append(protocol).append("://").append(getProperty(hostKey));
    String port = getProperty(portKey);
    if (!"80".equals(port)) {
      sb.append(':').append(port);
    }
    String contextRoot = getProperty(contextRootKey);
    if (!StringUtils.isEmpty(contextRoot)) {
      sb.append(contextRoot);
    }
    return sb.toString();

  }

  public void setProperty(CrudServiceConfigurationProperty property, String value) {
    properties.put(property.getPropertyKey(), value);
  }
}
