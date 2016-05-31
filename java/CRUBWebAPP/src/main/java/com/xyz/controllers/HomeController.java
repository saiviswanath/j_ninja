package com.xyz.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import com.xyz.form.beans.Address;
import com.xyz.form.beans.UpdateInputBean;
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

		List<Student> studentList = studentDao.findAllStudents();
		model.addAttribute("studentList", studentList);
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

		studentDao
				.createStudent(studentFormBeanToDtoConverter(studentFormBean));
		ModelAndView mav = new ModelAndView("newStudentSuccess");
		return mav;
	}

	@RequestMapping(value = "/updateInputForm.do", method = RequestMethod.GET)
	public ModelAndView updateInputForm() {
		ModelAndView mav = new ModelAndView("updateInputForm",
				"updateInputBean", new UpdateInputBean());
		return mav;
	}

	@RequestMapping(value = "/fetchUpdateFormDetails.do", method = RequestMethod.GET)
	public ModelAndView fetchUpdateFormDetails(
			@Valid @ModelAttribute("updateInputBean") UpdateInputBean updateInputBean,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("updateInputForm");
		}

		int studentId = studentDao.findStudentIdByName(
				updateInputBean.getFirstName(), updateInputBean.getLastName());

		if (studentId == 0) {
			ModelAndView mav = new ModelAndView("updateInputForm");
			mav.addObject("ErrorMessage", "No records Found");
			return mav;
		} else {
			Student studentDto = studentDao.findByName(
					updateInputBean.getFirstName(),
					updateInputBean.getLastName());
			return new ModelAndView("fetchUpdateFormDetails", "student",
					studentDtoToFormBeanConverter(studentDto));
		}
	}

	private com.xyz.form.beans.Student studentDtoToFormBeanConverter(
			Student studentDto) {
		System.out.println("*************************************"
				+ studentDto.toString());
		com.xyz.form.beans.Student student = new com.xyz.form.beans.Student();
		student.setFirstName(studentDto.getFirstName());
		student.setLastName(studentDto.getLastName());
		student.setGender(studentDto.getGender());
		student.setDOB(new java.util.Date(studentDto.getDOB().getTime()));
		student.setEmail(studentDto.getEmail());
		student.setMobileNumber(studentDto.getMobileNumber());
		student.setCourses(studentDto.getCourses());
		String addressString = studentDto.getAddress();
		String[] addressDetails = addressString.split(",");
		Address address = new Address();
		address.setHouseNo(addressDetails[0].trim());
		address.setStreet(addressDetails[1].trim());
		address.setArea(addressDetails[2].trim());
		String[] citypinSplit = addressDetails[3].split("-");
		address.setCity(citypinSplit[0].trim());
		address.setPin(Long.parseLong(citypinSplit[1].trim()));
		student.setAddress(address);
		return student;
	}

	private Student studentFormBeanToDtoConverter(
			com.xyz.form.beans.Student studentFormBean) {
		Student studentDto = new Student();
		studentDto.setFirstName(studentFormBean.getFirstName().trim());
		studentDto.setLastName(studentFormBean.getLastName().trim());
		studentDto.setGender(studentFormBean.getGender());
		studentDto.setDOB(new Date(studentFormBean.getDOB().getTime()));
		studentDto.setEmail(studentFormBean.getEmail().trim());
		studentDto.setMobileNumber(studentFormBean.getMobileNumber().trim());
		StringBuilder sb = new StringBuilder();
		sb.append(studentFormBean.getAddress().getHouseNo().trim()
				.replace(",", ";")
				+ ", ");
		sb.append(studentFormBean.getAddress().getStreet().trim()
				.replace(",", ";")
				+ ", ");
		sb.append(studentFormBean.getAddress().getArea().trim()
				.replace(",", ";")
				+ ", ");
		sb.append(studentFormBean.getAddress().getCity().trim()
				.replace(",", ";")
				+ "-");
		sb.append(studentFormBean.getAddress().getPin());
		studentDto.setAddress(sb.toString());
		studentDto.setCourses(studentFormBean.getCourses());
		return studentDto;
	}

	@RequestMapping(value = "/updateStudentDetails.do", method = RequestMethod.PUT)
	public ModelAndView updateStudentDetails(
			@Valid @ModelAttribute com.xyz.form.beans.Student student,
			BindingResult bindingResult, final HttpServletResponse res) {
		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("fetchUpdateFormDetails",
					"student",
					studentDtoToFormBeanConverter(studentDao.findByName(
							student.getFirstName(), student.getLastName())));
			return mav;
		}
		studentDao.updateStudent(studentFormBeanToDtoConverter(student));
		res.setHeader("Cache-Control", "no-cache");
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}

	@RequestMapping(value = "/deleteInputForm.do", method = RequestMethod.GET)
	public ModelAndView deleteInputForm() {
		ModelAndView mav = new ModelAndView("deleteFormPage",
				"updateInputBean", new UpdateInputBean());
		return mav;
	}

	@RequestMapping(value = "/deleteFormDetails.do", method = RequestMethod.DELETE)
	public ModelAndView deleteFormDetails(
			@Valid @ModelAttribute UpdateInputBean updateInputBean,
			BindingResult bindingResult, final HttpServletResponse res) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("deleteFormPage");
		}
		boolean deletedRecord = studentDao.deteteStudentByName(
				updateInputBean.getFirstName(), updateInputBean.getLastName());
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
