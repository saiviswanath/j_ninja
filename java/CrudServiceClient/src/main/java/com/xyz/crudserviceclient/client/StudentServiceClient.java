package com.xyz.crudserviceclient.client;

import org.springframework.beans.factory.annotation.Autowired;

import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.config.CrudServiceConfiguration;
import com.xyz.crudserviceclient.response.StudentsResponse;
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
}
