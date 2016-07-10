package com.xyz.authenticationserviceclient.utilitybeans;

public class PagedCommand {

  private int first = 0;
  private int max = 15;

  public PagedCommand() {}

  public PagedCommand(int max) {
    this.max = max;
  }

  public int getFirst() {
    return first;
  }

  public void setFirst(int first) {
    this.first = first;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

}
