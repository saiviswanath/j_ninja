package com.xyz.crudserviceclient.response;

import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.utilitybeans.PagedResult;

public class StudentsResponse extends RestFulResponse {
  private PagedResult<StudentBean> students;

  public StudentsResponse() {}

  public StudentsResponse(PagedResult<StudentBean> students) {
    this.students = students;
  }

  public PagedResult<StudentBean> getStudents() {
    return students;
  }

  public void setStudents(PagedResult<StudentBean> students) {
    this.students = students;
  }


}
