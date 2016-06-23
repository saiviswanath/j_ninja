package com.xyz.config;

import java.util.Properties;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netflix.config.ConfigurationManager;
import com.xyz.exceptions.ConfigurationException;

public class Configuration {
  private static final Logger logger = Logger.getLogger(Configuration.class);

  private final Properties properties;
  private AbstractConfiguration config;

  public Configuration(Properties properties) {
    this.properties = properties;
    logger.debug("new ConfigurationManager with " + properties);
  }

  public Configuration() {
    this.properties = null;
    logger.debug("new ConfigurationManager with properties null");
  }

  public AbstractConfiguration getConfig() {
    logger.debug("Getting ConfigurationManager");
    if (config == null) {
      config = ConfigurationManager.getConfigInstance();
      logger.debug("Getting ConfigurationManager with " + config.getKeys());
    }

    return config;
  }

  public String getProperty(ConfigurationProperty key) {
    logger.debug("Requesting property=" + key);
    String property = null;
    if (properties != null) {
      property = properties.getProperty(key.getPropertyKey());
      if (property == null) {
        if (key.getDefaultValue() != null) {
          return key.getDefaultValue();
        }
        throw new ConfigurationException("no definition for property: " + key.getPropertyKey());
      }
    } else {
      property = (String) getConfig().getProperty(key.getPropertyKey());
      logger.debug("Config property=" + key.getPropertyKey() + " returning value=" + property);
    }
    return property;
  }

  public int getPropertyInteger(ConfigurationProperty key) {
    return Integer.parseInt(getProperty(key));
  }

  public long getPropertyLong(ConfigurationProperty key) {
    return Long.parseLong(getProperty(key));
  }

  public double getPropertyDouble(ConfigurationProperty key) {
    return Double.parseDouble(getProperty(key));
  }

  public boolean getPropertyBoolean(ConfigurationProperty key) {
    return Boolean.parseBoolean(getProperty(key));
  }

  public String getPropertyHttpUrl(ConfigurationProperty hostKey, ConfigurationProperty portKey,
      ConfigurationProperty contextRootKey) {
    return getUrl("http", hostKey, portKey, contextRootKey);
  }

  public String getPropertyHttpsUrl(ConfigurationProperty hostKey, ConfigurationProperty portKey,
      ConfigurationProperty contextRootKey) {
    return getUrl("https", hostKey, portKey, contextRootKey);
  }

  private String getUrl(String protocol, ConfigurationProperty hostKey,
      ConfigurationProperty portKey, ConfigurationProperty contextRootKey) {
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

  public void setProperty(ConfigurationProperty property, String value) {
    properties.put(property.getPropertyKey(), value);
  }
}
