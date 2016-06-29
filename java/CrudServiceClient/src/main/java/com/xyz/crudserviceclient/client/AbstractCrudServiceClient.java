package com.xyz.crudserviceclient.client;

import java.util.Collection;
import java.util.Map;

import com.xyz.crudserviceclient.config.CrudServiceConfiguration;
import com.xyz.crudserviceclient.config.CrudServiceConfigurationProperty;
import com.xyz.crudserviceclient.utilitybeans.PagedCommand;
import com.xyz.crudserviceclient.utilitybeans.SortablePagedCommand;

public class AbstractCrudServiceClient {
  private final String baseUrl;

  public AbstractCrudServiceClient(CrudServiceConfiguration config) {
    this.baseUrl = getBaseUrl(config);
  }

  protected String getBaseUrl() {
    return baseUrl;
  }

  private static String getBaseUrl(CrudServiceConfiguration config) {
    boolean useHttps = config.getPropertyBoolean(CrudServiceConfigurationProperty.CRUDSERVICE_USE_HTTPS);
    if (useHttps)
      return config.getPropertyHttpsUrl(CrudServiceConfigurationProperty.CRUDSERVICE_HOST,
          CrudServiceConfigurationProperty.CRUDSERVICE_PORT, CrudServiceConfigurationProperty.CRUDSERVICE_CONTEXT_PATH);
    else
      return config.getPropertyHttpUrl(CrudServiceConfigurationProperty.CRUDSERVICE_HOST,
          CrudServiceConfigurationProperty.CRUDSERVICE_PORT, CrudServiceConfigurationProperty.CRUDSERVICE_CONTEXT_PATH);
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

  protected String getUrl(String path, PagedCommand pagedCommand) {
    return getUrl(path, pagedCommand, null);
  }

  protected String getUrl(String path, PagedCommand pagedCommand, Map<String, ?> extraParams) {
    StringBuilder sb = getUrlBuffer(path);
    appendPagedCommand(sb, pagedCommand);
    appendParams(sb, extraParams);
    return sb.toString();
  }

  private void appendPagedCommand(StringBuilder sb, PagedCommand pagedCommand) {
    if (pagedCommand != null) {
      sb.append(getDelimiter(sb)).append("first=").append(pagedCommand.getFirst());
      sb.append("&max=").append(pagedCommand.getMax());
      if (pagedCommand instanceof SortablePagedCommand) {
        SortablePagedCommand sortable = (SortablePagedCommand) pagedCommand;
        if (sortable.getSort() != null) {
          appendParam(sb, "sortBy", sortable.getSort());
          if (sortable.getSortDirection() != null) {
            appendParam(sb, "sortDirection", sortable.getSortDirection());
          }
        }
      }
    }
  }
}
