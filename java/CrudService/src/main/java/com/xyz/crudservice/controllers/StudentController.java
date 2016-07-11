package com.xyz.crudservice.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import com.xyz.crudservice.converters.StudentConverter;
import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.enums.SortColumn;
import com.xyz.crudservice.exceptions.CrudServiceException;
import com.xyz.crudservice.services.HtmlToPDFServiceImpl;
import com.xyz.crudservice.services.StudentService;
import com.xyz.crudservice.utilbeans.PageAndSortData;
import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.enums.FailureCause;
import com.xyz.crudserviceclient.enums.SortDirection;
import com.xyz.crudserviceclient.response.BooleanResponse;
import com.xyz.crudserviceclient.response.CoursesResponse;
import com.xyz.crudserviceclient.response.StudentIdResponse;
import com.xyz.crudserviceclient.response.StudentResponse;
import com.xyz.crudserviceclient.response.StudentsResponse;
import com.xyz.crudserviceclient.response.VoidResponse;
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
  @Autowired
  private HtmlToPDFServiceImpl htmlToPdf;

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

  @RequestMapping(value = "/courses", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CoursesResponse> getAllCourses() {
    List<String> courses = null;
    try {
      courses = studentService.getAllCourses();
    } catch (Exception e) {
      throw new CrudServiceException(FailureCause.UNEXPECTED,
          "Unable to fetch all courses from DB", e);
    }
    return new ResponseEntity<>(new CoursesResponse(courses), HttpStatus.OK);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VoidResponse> createStudent(@RequestBody StudentBean studentBean,
      final HttpServletRequest req) {
    HttpHeaders headers = null;
    try {
      StudentDto studentDto = studentConverter.studentBeanToStudentDtoConverter(studentBean);
      studentService.createStudent(studentDto);
      headers = new HttpHeaders();

      String requestUrl = req.getRequestURL().toString();
      StringBuffer requestUrlSb =
          new StringBuffer(requestUrl.substring(0, requestUrl.length() - 7));
      String uri =
          UriUtils.encode(requestUrlSb.append("/").append(studentBean.getFirstName()).append("/")
              .append(studentBean.getLastName()).toString(), "UTF-8");
      headers.setLocation(new URI(uri));
    } catch (URISyntaxException e1) {
      throw new CrudServiceException(FailureCause.BAD_URI_SYNTAX,
          "Unable to create student due to bad return location url syntax", e1);
    } catch (UnsupportedEncodingException e2) {
      throw new CrudServiceException(FailureCause.UNSUPPORTED_ENCODING,
          "Unable to craete student due to unsupported return location url encoding", e2);
    } catch (Exception e3) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Unable to create student", e3);
    }
    return new ResponseEntity<>(new VoidResponse(), headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{firstname}/{lastname}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StudentResponse> getStudentByName(@PathVariable Map<String, String> map) {
    String firstName = map.get("firstname");
    String lastName = map.get("lastname");
    if (!StringUtils.isNotEmpty(firstName) || !StringUtils.isNotEmpty(lastName)) {
      throw new CrudServiceException(FailureCause.MISSING_PARAM,
          "Missing firstName or lastName in request");
    }

    StudentDto studentDto = null;
    try {
      studentDto = studentService.findByName(firstName, lastName);
      if (studentDto == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Unable to fetch Student by name", e);
    }
    StudentBean studentBean = studentConverter.studentDtoToStudentBeanConverter(studentDto);
    return new ResponseEntity<>(new StudentResponse(studentBean), HttpStatus.OK);
  }

  @RequestMapping(value = "/findid/{firstname}/{lastname}")
  public ResponseEntity<StudentIdResponse> getStudentIdByName(@PathVariable Map<String, String> map) {
    String firstName = map.get("firstname");
    String lastName = map.get("lastname");
    if (!StringUtils.isNotEmpty(firstName) || !StringUtils.isNotEmpty(lastName)) {
      throw new CrudServiceException(FailureCause.MISSING_PARAM,
          "Missing firstName or lastName in request");
    }

    int studentId = 0;
    try {
      studentId = studentService.getStudentIdByName(firstName, lastName);
      if (studentId == 0) {
        return new ResponseEntity<StudentIdResponse>(new StudentIdResponse(studentId),
            HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Unable to fetch StudentId by name",
          e);
    }
    return new ResponseEntity<>(new StudentIdResponse(studentId), HttpStatus.OK);
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VoidResponse> updateStudent(@RequestBody StudentBean studentBean) {
    try {
      StudentDto studentDto = studentConverter.studentBeanToStudentDtoConverter(studentBean);
      studentService.updateStudent(studentDto);
    } catch (Exception e) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Unable to update Student", e);
    }
    return new ResponseEntity<>(new VoidResponse(), HttpStatus.OK);
  }

  @RequestMapping(value = "/delete/{firstname}/{lastname}")
  public ResponseEntity<BooleanResponse> deleteStudentByName(@PathVariable Map<String, String> map) {
    String firstName = map.get("firstname");
    String lastName = map.get("lastname");
    if (!StringUtils.isNotEmpty(firstName) || !StringUtils.isNotEmpty(lastName)) {
      throw new CrudServiceException(FailureCause.MISSING_PARAM,
          "Missing firstName or lastName in request");
    }

    boolean deletedRecord = false;
    try {
      deletedRecord = studentService.deleteStudentByName(firstName, lastName);
    } catch (Exception e) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Unable to delete Student", e);
    }
    return new ResponseEntity<>(new BooleanResponse(deletedRecord), HttpStatus.OK);
  }
  
  @RequestMapping(value="/getpdf", method= RequestMethod.PUT)
  public void htmlToPdf(@RequestBody String body, final HttpServletResponse response) throws IOException {
    byte[] bytes = htmlToPdf.convertHtmlToPDF(Jsoup.parse(body));
    InputStream is = new ByteArrayInputStream(bytes);
    response.setContentLength(bytes.length);
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment");
    response.setHeader("filename", "outpdf");
    IOUtils.copy(is, response.getOutputStream());
    response.flushBuffer();
  }
}
