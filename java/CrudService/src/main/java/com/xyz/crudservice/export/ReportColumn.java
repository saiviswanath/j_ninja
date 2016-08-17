package com.xyz.crudservice.export;

public class ReportColumn {
  private String label;
  private int width;
  private short alignment;

  public ReportColumn() {
    label = "";
    width = 0;
    alignment = 0;
  }

  public ReportColumn(String label, int width, short alignment) {
    this.label = label;
    this.width = width;
    this.alignment = alignment;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public short getAlignment() {
    return alignment;
  }

  public void setAlignment(short alignment) {
    this.alignment = alignment;
  }
}
