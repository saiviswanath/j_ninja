package com.xyz.crudservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xyz.crudservice.converters.StudentConverter;
import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.enums.SortColumn;
import com.xyz.crudservice.exceptions.CrudServiceException;
import com.xyz.crudservice.services.StudentService;
import com.xyz.crudservice.utilbeans.PageAndSortData;
import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.enums.FailureCause;
import com.xyz.crudserviceclient.enums.SortDirection;
import com.xyz.crudserviceclient.response.StudentsResponse;
import com.xyz.crudserviceclient.utilitybeans.PagedResult;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
  private static final Integer ZERO = new Integer(0);
  private static final Integer MAX_VALUE = new Integer(Integer.MAX_VALUE);

  @Autowired
  private StudentService studentService;
  @Autowired
  private StudentConverter studentConverter;

  @RequestMapping(value = "", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StudentsResponse> getAllStudents(@RequestParam(value = "first",
  required = false) Integer first, @RequestParam(value = "max", required = false) Integer max,
  @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(
      value = "sortDirection", required = false) SortDirection sortDir) {
    if (first == null) {
      first = ZERO;
    }

    if (max == null) {
      max = MAX_VALUE;
    }

    if (sortDir == null) {
      sortDir = SortDirection.ASCENDING;
    }

    if (sortBy == null) {
      sortBy = SortColumn.FIRSTNAME.getColName();
    }

    // Encapsulate the pagination and sort params into one object
    PageAndSortData pageAndSortData = new PageAndSortData();
    pageAndSortData.setFirst(first);
    pageAndSortData.setMax(max);
    pageAndSortData.setSortBy(sortBy);
    pageAndSortData.setSortDirection(sortDir);

    List<StudentBean> studentBeans = null;
    try {
      List<StudentDto> students = studentService.getAllStudents(pageAndSortData);
      if (students != null && students.size() > 0) {
        studentBeans = new ArrayList<>(students.size());
        for (StudentDto sDto : students) {
          studentBeans.add(studentConverter.studentDtoToStudentBeanConverter(sDto));
        }
      } else {
        studentBeans = new ArrayList<>(0);
      }
    } catch (Exception e) {
      throw new CrudServiceException(FailureCause.UNEXPECTED,
          "Unable to fetch all students from DB", e);
    }
    PagedResult<StudentBean> pagedResult = new PagedResult<>(studentBeans, studentBeans.size());
    pagedResult.setUnfilteredItems(studentService.getStudentCount());
    return new ResponseEntity<>(new StudentsResponse(pagedResult), HttpStatus.OK);
  }

}
