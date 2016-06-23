package com.xyz.client;

import java.util.Collection;
import java.util.Map;

import com.xyz.config.Configuration;
import com.xyz.config.ConfigurationProperty;

public class AbstractAuthClient {
  private final String baseUrl;

  public AbstractAuthClient(Configuration config) {
    this.baseUrl = getBaseUrl(config);
  }

  protected String getBaseUrl() {
    return baseUrl;
  }

  private static String getBaseUrl(Configuration config) {
    boolean useHttps = config.getPropertyBoolean(ConfigurationProperty.AUTH_USE_HTTPS);
    if (useHttps)
      return config.getPropertyHttpsUrl(ConfigurationProperty.AUTH_HOST,
          ConfigurationProperty.AUTH_PORT, ConfigurationProperty.AUTH_CONTEXT_PATH);
    else
      return config.getPropertyHttpUrl(ConfigurationProperty.AUTH_HOST,
          ConfigurationProperty.AUTH_PORT, ConfigurationProperty.AUTH_CONTEXT_PATH);
  }

  protected String getUrl(String path) {
    return getBaseUrl() + path;
  }

  protected String getUrl(String path, Map<String, ?> extraParams) {
    StringBuilder sb = getUrlBuffer(path);
    appendParams(sb, extraParams);
    return sb.toString();
  }

  protected StringBuilder getUrlBuffer(String path) {
    return new StringBuilder(getBaseUrl()).append(path);
  }

  protected char getDelimiter(StringBuilder sb) {
    return sb.indexOf("?") < 0 ? '?' : '&';
  }

  protected void appendParams(StringBuilder sb, Map<String, ?> extraParams) {
    if (extraParams != null) {
      for (String param : extraParams.keySet()) {
        appendParam(sb, param, extraParams.get(param));
      }
    }
  }

  protected void appendParam(StringBuilder sb, String param, Object value) {
    if (value instanceof Collection<?>) {
      for (Object o : (Collection<?>) value) {
        sb.append(getDelimiter(sb)).append(param).append('=').append("" + o);
      }
    } else {
      sb.append(getDelimiter(sb)).append(param).append('=').append("" + value);
    }
  }
}
