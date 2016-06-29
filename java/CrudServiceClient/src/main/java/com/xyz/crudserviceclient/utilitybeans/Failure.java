package com.xyz.crudserviceclient.utilitybeans;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xyz.crudserviceclient.enums.FailureCause;

public class Failure {
  private FailureCause failureCause;
  private String description;

  public Failure() {}

  public Failure(FailureCause failureCause, String description) {
    this.failureCause = failureCause;
    this.description = description;
  }

  public FailureCause getFailureCause() {
    return failureCause;
  }

  public void setFailureCause(FailureCause failureCause) {
    this.failureCause = failureCause;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
