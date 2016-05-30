package com.xyz.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xyz.dao.StudentDAO;
import com.xyz.dto.Student;
import com.xyz.propertyeditors.LongPropertyEditor;

@Controller
public class HomeController {
	@Autowired
	private StudentDAO studentDao;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(java.util.Date.class, "DOB",
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		dataBinder.registerCustomEditor(Long.class, "address.pin",
				new LongPropertyEditor(true));
	}

	@RequestMapping(value = "/getHomePage.do", method = RequestMethod.GET)
	public ModelAndView getHomePage() {
		ModelAndView mav = new ModelAndView("home");
		List<Student> studentList = studentDao.findAllStudents();
		mav.addObject("studentList", studentList);
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

	@RequestMapping(value = "/newStudentPage.do", method = RequestMethod.GET)
	public ModelAndView newStudentPage() {
		ModelAndView mav = new ModelAndView("newStudentPage", "student",
				new com.xyz.form.beans.Student());
		return mav;
	}

	@RequestMapping(value = "/newStudentDetails.do", method = RequestMethod.POST)
	public ModelAndView newStudentDetails(
			@Valid @ModelAttribute("student") com.xyz.form.beans.Student studentFormBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("newStudentPage");
		}
		Student studentDto = new Student();
		studentDto.setFirstName(studentFormBean.getFirstName());
		studentDto.setLastName(studentFormBean.getLastName());
		studentDto.setGender(studentFormBean.getGender());
		studentDto.setDOB(new Date(studentFormBean.getDOB().getTime()));
		studentDto.setEmail(studentFormBean.getEmail());
		studentDto.setMobileNumber(studentFormBean.getMobileNumber());
		StringBuilder sb = new StringBuilder();
		sb.append(studentFormBean.getAddress().getHouseNo() + ", ");
		sb.append(studentFormBean.getAddress().getStreet() + ", ");
		sb.append(studentFormBean.getAddress().getArea() + ", ");
		sb.append(studentFormBean.getAddress().getCity() + "-");
		sb.append(studentFormBean.getAddress().getPin());
		studentDto.setAddress(sb.toString());
		studentDto.setCourses(studentFormBean.getCourses());
		studentDao.createStudent(studentDto);
		ModelAndView mav = new ModelAndView("newStudentSuccess");
		return mav;
	}
}
