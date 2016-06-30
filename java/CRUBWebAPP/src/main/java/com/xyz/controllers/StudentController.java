package com.xyz.controllers;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.client.StudentServiceClient;
import com.xyz.crudserviceclient.utilitybeans.PagedResult;
import com.xyz.crudserviceclient.utilitybeans.SortablePagedCommand;
import com.xyz.dao.StudentDAO;
import com.xyz.dto.Student;
import com.xyz.form.beans.UpdateInputBean;
import com.xyz.propertyeditors.LongPropertyEditor;
import com.xyz.util.DataConverter;

@Controller
public class StudentController {
  
  @Autowired
  private StudentServiceClient studentServiceClient;
  @Autowired
  private StudentDAO studentDao;
  @Autowired
  private DataConverter dataConverter;

  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {
    dataBinder.registerCustomEditor(java.util.Date.class, "DOB", new CustomDateEditor(
        new SimpleDateFormat("yyyy-MM-dd"), true));
    dataBinder.registerCustomEditor(Long.class, "address.pin", new LongPropertyEditor(true));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_READ')")
  @RequestMapping(value = "/getHomePage.do", method = RequestMethod.GET)
  public ModelAndView getHomePage(@RequestParam(value = "first", required = false) Integer first,
      @RequestParam(value = "max", required = false) Integer max, @RequestParam(value = "sortBy",
          required = false) String sortBy,
      @RequestParam(value = "sortDirection", required = false) String sortDir) {
    
    ModelAndView mav = new ModelAndView("home");

    SortablePagedCommand sortablePagedCommand = new SortablePagedCommand();
    sortablePagedCommand.setFirst(first);
    sortablePagedCommand.setMax(max);
    sortablePagedCommand.setSort(sortBy);
    sortablePagedCommand.setSortDir(sortDir);
    
    PagedResult<StudentBean> students = studentServiceClient.getAllStudents(sortablePagedCommand);
    List<StudentBean> studentList = students.getItems();
    
    int noOfRecords = students.getUnfilteredItems();
    int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / max);
    
    mav.addObject("sortByField", sortBy);
    mav.addObject("sortDirField", sortDir);
    mav.addObject("studentList", studentList);
    mav.addObject("noOfPages", noOfPages);
    mav.addObject("currentPage", first);

    return mav;
  }

  @ModelAttribute
  public void addCourseListObject(Model model) {
    List<String> courseList = studentDao.findAllCourses();
    Map<String, String> courseMap = new LinkedHashMap<>();
    for (String course : courseList) {
      courseMap.put(course, course);
    }
    model.addAttribute("courseList", courseMap);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/newStudentPage.do", method = RequestMethod.GET)
  public ModelAndView newStudentPage() {
    ModelAndView mav =
        new ModelAndView("newStudentPage", "student", new com.xyz.form.beans.Student());
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/newStudentDetails.do", method = RequestMethod.POST)
  public ModelAndView newStudentDetails(
      @Valid @ModelAttribute("student") com.xyz.form.beans.Student studentFormBean,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("newStudentPage");
    }

    studentDao.createStudent(dataConverter.studentFormBeanToDtoConverter(studentFormBean));
    ModelAndView mav = new ModelAndView("newStudentSuccess");
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/updateInputForm.do", method = RequestMethod.GET)
  public ModelAndView updateInputForm() {
    ModelAndView mav =
        new ModelAndView("updateInputForm", "updateInputBean", new UpdateInputBean());
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/fetchUpdateFormDetails.do", method = RequestMethod.GET)
  public ModelAndView fetchUpdateFormDetails(
      @Valid @ModelAttribute("updateInputBean") UpdateInputBean updateInputBean,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("updateInputForm");
    }

    int studentId =
        studentDao.findStudentIdByName(updateInputBean.getFirstName(),
            updateInputBean.getLastName());

    if (studentId == 0) {
      ModelAndView mav = new ModelAndView("updateInputForm");
      mav.addObject("ErrorMessage", "No records Found");
      return mav;
    } else {
      Student studentDto =
          studentDao.findByName(updateInputBean.getFirstName(), updateInputBean.getLastName());
      return new ModelAndView("fetchUpdateFormDetails", "student",
          dataConverter.studentDtoToFormBeanConverter(studentDto));
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/updateStudentDetails.do", method = RequestMethod.PUT)
  public ModelAndView updateStudentDetails(
      @Valid @ModelAttribute com.xyz.form.beans.Student student, BindingResult bindingResult,
      final HttpServletResponse res) {
    if (bindingResult.hasErrors()) {
      ModelAndView mav =
          new ModelAndView("fetchUpdateFormDetails", "student",
              dataConverter.studentDtoToFormBeanConverter(studentDao.findByName(
                  student.getFirstName(), student.getLastName())));
      return mav;
    }
    studentDao.updateStudent(dataConverter.studentFormBeanToDtoConverter(student));
    res.setHeader("Cache-Control", "no-cache");
    ModelAndView mav = new ModelAndView("home");
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/deleteInputForm.do", method = RequestMethod.GET)
  public ModelAndView deleteInputForm() {
    ModelAndView mav = new ModelAndView("deleteFormPage", "updateInputBean", new UpdateInputBean());
    return mav;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WRITE')")
  @RequestMapping(value = "/deleteFormDetails.do", method = RequestMethod.DELETE)
  public ModelAndView deleteFormDetails(@Valid @ModelAttribute UpdateInputBean updateInputBean,
      BindingResult bindingResult, final HttpServletResponse res) {
    if (bindingResult.hasErrors()) {
      return new ModelAndView("deleteFormPage");
    }
    boolean deletedRecord =
        studentDao.deteteStudentByName(updateInputBean.getFirstName(),
            updateInputBean.getLastName());
    if (deletedRecord) {
      res.setHeader("Cache-Control", "no-cache");
      return new ModelAndView("home");
    } else {
      ModelAndView mav = new ModelAndView("deleteFormPage");
      mav.addObject("ErrorMessage", "No records Found");
      return mav;
    }
  }
}
