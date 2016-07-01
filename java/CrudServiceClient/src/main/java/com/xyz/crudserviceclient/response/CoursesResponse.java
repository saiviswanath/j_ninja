package com.xyz.crudserviceclient.response;

import java.util.List;

public class CoursesResponse extends RestFulResponse {
  private List<String> courses;

  public CoursesResponse() {}

  public CoursesResponse(List<String> courses) {
    this.courses = courses;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }
}
