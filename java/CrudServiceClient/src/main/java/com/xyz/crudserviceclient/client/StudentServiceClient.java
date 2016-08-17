package com.xyz.crudserviceclient.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.config.CrudServiceConfiguration;
import com.xyz.crudserviceclient.response.BooleanResponse;
import com.xyz.crudserviceclient.response.CoursesResponse;
import com.xyz.crudserviceclient.response.StudentIdResponse;
import com.xyz.crudserviceclient.response.StudentResponse;
import com.xyz.crudserviceclient.response.StudentsResponse;
import com.xyz.crudserviceclient.response.VoidResponse;
import com.xyz.crudserviceclient.utilitybeans.PagedCommand;
import com.xyz.crudserviceclient.utilitybeans.PagedResult;

public class StudentServiceClient extends AbstractCrudServiceClient {
  public static final int MAX_PAGED_RECORDS = 30;
  @Autowired
  private CrudServiceRestTemplate crudServiceRestTemplate;

  public StudentServiceClient(CrudServiceConfiguration config) {
    super(config);
  }

  public PagedResult<StudentBean> getAllStudents(PagedCommand pagedCommand) {
    if (pagedCommand == null) {
      pagedCommand = new PagedCommand(MAX_PAGED_RECORDS);
    }
    String url = getUrl("/student", pagedCommand);
    StudentsResponse studentsResponse =
        crudServiceRestTemplate.getForObject(url, StudentsResponse.class);
    studentsResponse.verify();
    return studentsResponse.getStudents();
  }

  public List<String> getAllCourses() {
    String url = getUrl("/student/courses");
    CoursesResponse coursesResponse =
        crudServiceRestTemplate.getForObject(url, CoursesResponse.class);
    coursesResponse.verify();
    return coursesResponse.getCourses();
  }

  public void createStudent(StudentBean studentBean) {
    String url = getUrl("/student/create");
    VoidResponse voidResponse =
        crudServiceRestTemplate.postForObject(url, studentBean, VoidResponse.class);
    voidResponse.verify();
  }

  public StudentBean getStudentByName(String firstName, String lastName) {
    String url = getUrl("/student/{firstname}/{lastname}");

    Map<String, String> varMap = new HashMap<>();
    varMap.put("firstname", firstName);
    varMap.put("lastname", lastName);
    Map<String, String> unmodVarMap = Collections.unmodifiableMap(varMap);


    StudentResponse studentResponse = null;
    try {
      studentResponse =
          crudServiceRestTemplate.getForObject(url, StudentResponse.class, unmodVarMap);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        return null;
      }
    }
    studentResponse.verify();
    return studentResponse.getStudentBean();
  }

  public int getStudentIdByName(String firstName, String lastName) {
    String url = getUrl("/student/findid/{firstname}/{lastname}");
    Map<String, String> varMap = new HashMap<>();
    varMap.put("firstname", firstName);
    varMap.put("lastname", lastName);
    Map<String, String> unmodVarMap = Collections.unmodifiableMap(varMap);

    StudentIdResponse studentIdResponse = null;
    try {
      studentIdResponse =
          crudServiceRestTemplate.getForObject(url, StudentIdResponse.class, unmodVarMap);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        return 0;
      }
    }
    studentIdResponse.verify();
    return studentIdResponse.getStudentId();
  }

  public void updateStudent(StudentBean studentBean) {
    String url = getUrl("/student/update");
    HttpEntity<StudentBean> studentBeanHttpEntity = new HttpEntity<StudentBean>(studentBean);
    ResponseEntity<VoidResponse> responseEntity =
        crudServiceRestTemplate.exchange(url, HttpMethod.PUT, studentBeanHttpEntity,
            VoidResponse.class);
    VoidResponse voidResponse = responseEntity.getBody();
    voidResponse.verify();
  }

  public boolean deleteStudentByName(String firstName, String lastName) {
    String url = getUrl("/student/delete/{firstname}/{lastname}");
    Map<String, String> varMap = new HashMap<>();
    varMap.put("firstname", firstName);
    varMap.put("lastname", lastName);
    Map<String, String> unmodVarMap = Collections.unmodifiableMap(varMap);

    ResponseEntity<BooleanResponse> responseEntity =
        crudServiceRestTemplate.exchange(url, HttpMethod.DELETE, null, BooleanResponse.class,
            unmodVarMap);
    BooleanResponse booleanResponse = responseEntity.getBody();
    booleanResponse.verify();
    return booleanResponse.isResponse();
  }

  public byte[] exportStudentsToExcel(PagedCommand pagedCommand) {
    if (pagedCommand == null) {
      pagedCommand = new PagedCommand(MAX_PAGED_RECORDS);
    }
    String url = getUrl("/student/export", pagedCommand);
    return crudServiceRestTemplate.getForObject(url, byte[].class);
  }
}
