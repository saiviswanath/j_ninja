package com.xyz.crudserviceclient.response;

public class StudentIdResponse extends RestFulResponse {
  private int studentId;

  public StudentIdResponse() {}

  public StudentIdResponse(int studentId) {
    this.studentId = studentId;
  }

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }
}
