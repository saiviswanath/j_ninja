package com.xyz.crudserviceclient.response;

import com.xyz.crudserviceclient.beans.StudentBean;

public class StudentResponse extends RestFulResponse {
  private StudentBean studentBean;

  public StudentResponse() {}

  public StudentResponse(StudentBean studentBean) {
    this.studentBean = studentBean;
  }

  public StudentBean getStudentBean() {
    return studentBean;
  }

  public void setStudentBean(StudentBean studentBean) {
    this.studentBean = studentBean;
  }

}
