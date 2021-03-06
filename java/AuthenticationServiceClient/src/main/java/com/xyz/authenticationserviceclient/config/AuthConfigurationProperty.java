package com.xyz.authenticationserviceclient.config;

public enum AuthConfigurationProperty {
  AUTH_HOST("app.auth.host", "localhost"), AUTH_CONTEXT_PATH("app.auth.contextPath",
      "/AuthenticationService"), AUTH_PORT("app.auth.port", "8080"), AUTH_USE_HTTPS(
      "app.auth.useHttps", "false");

  private final String propertyKey;
  private final String defaultValue;

  private AuthConfigurationProperty(String propertyKey) {
    this.propertyKey = propertyKey;
    this.defaultValue = null;
  }

  private AuthConfigurationProperty(String propertyKey, String defaultValue) {
    this.propertyKey = propertyKey;
    this.defaultValue = defaultValue;
  }

  public String getPropertyKey() {
    return propertyKey;
  }

  public String getDefaultValue() {
    return defaultValue;
  }
}
