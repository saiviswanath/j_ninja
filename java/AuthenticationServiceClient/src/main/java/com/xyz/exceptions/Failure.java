package com.xyz.exceptions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Failure {
  private ExceptionCause cause;
  private String description;

  public Failure() {}

  public Failure(ExceptionCause cause, String description) {
    this.cause = cause;
    this.description = description;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public ExceptionCause getCause() {
    return cause;
  }

  public void setCause(ExceptionCause cause) {
    this.cause = cause;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
