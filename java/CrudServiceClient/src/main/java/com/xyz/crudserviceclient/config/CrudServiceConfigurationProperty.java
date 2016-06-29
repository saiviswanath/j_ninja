package com.xyz.crudserviceclient.config;

public enum CrudServiceConfigurationProperty {
  CRUDSERVICE_HOST("app.crudservice.host", "localhost"), CRUDSERVICE_CONTEXT_PATH(
      "app.crudservice.contextpath", "/CrudService"), CRUDSERVICE_PORT("app.crudservice.port",
          "8080"), CRUDSERVICE_USE_HTTPS("app.crudservice.useHttps", "false");

  private final String propertyKey;
  private final String defaultValue;

  private CrudServiceConfigurationProperty(String propertyKey) {
    this.propertyKey = propertyKey;
    this.defaultValue = null;
  }

  private CrudServiceConfigurationProperty(String propertyKey, String defaultValue) {
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
