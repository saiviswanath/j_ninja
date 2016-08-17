package com.xyz.controllers;
 
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.xyz.crudserviceclient.beans.StudentBean;
import com.xyz.crudserviceclient.client.StudentServiceClient;
import com.xyz.crudserviceclient.utilitybeans.PagedResult;
 
@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {
       private MockMvc mockMvc;
       @InjectMocks
       private StudentController controller;
       @Mock
       private StudentServiceClient stuClient;
 
       @Before
       public void setUp() {
              MockitoAnnotations.initMocks(this);
              mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
              Mockito.reset(stuClient);
       }
 
       @SuppressWarnings("unchecked")
       @Test
       public void testGetHomePage() throws Exception {
              Calendar cal = Calendar.getInstance();
              StudentBean sb1 = new StudentBean();
              sb1.setFirstName("Sai");
              sb1.setLastName("Palaparthi");
              sb1.setGender("Male");
              sb1.setDOB(cal.getTime());
              sb1.setEmail("v.p@gmail.com");
              sb1.setMobileNumber("8121157247");
              sb1.setAddress("Huda");
              sb1.setCourses(Arrays.asList("Math", "Chem"));
 
              StudentBean sb2 = new StudentBean();
              sb2.setFirstName("Viswanath");
              sb2.setLastName("Palaparthi");
              sb2.setGender("Male");
              sb2.setDOB(cal.getTime());
              sb2.setEmail("v.p@gmail.com");
              sb2.setMobileNumber("8121157248");
              sb2.setAddress("Huda");
              sb2.setCourses(Arrays.asList("Math"));
 
              PagedResult<StudentBean> pResult = new PagedResult<StudentBean>(
                           Arrays.asList(sb1, sb2), 2);
              pResult.setFirst(0);
              pResult.setUnfilteredItems(2);
             
              Mockito.when(stuClient.getAllStudents(Mockito.any())).thenReturn(pResult);
              Mockito.when(stuClient.getAllCourses()).thenReturn(Arrays.asList("Math", "Chem"));
             
              Integer first = 0;
              Integer max = 5;
              String sortBy = "firstname";
              String sortDir = "asc";
 
              mockMvc.perform(
                           get("/getHomePage.do").param("first", String.valueOf(first))
                                         .param("max", String.valueOf(max))
                                         .param("sortBy", sortBy)
                                         .param("sortDirection", sortDir))
                           .andExpect(status().isOk())
                           .andExpect(view().name("home"))
                           .andExpect(forwardedUrl("home"))
                           .andExpect(model().attribute("sortByField", is(sortBy)))
                           .andExpect(model().attribute("sortDirField", is(sortDir)))
                           .andExpect(model().attribute("noOfPages", 1))
                           .andExpect(model().attribute("currentPage", is(first)))
                           .andExpect(model().attribute("studentList", hasSize(2)))
                          
                           .andExpect(model().attribute("courseList",
                                         allOf(hasEntry("Math", "Math"),
                                                       hasEntry("Chem", "Chem")
                                         )))
                           .andExpect(
                                         model().attribute(
                                                       "studentList",
                                                       hasItem(allOf(
                                                                     hasProperty("firstName", is("Sai")),
                                                                     hasProperty("lastName",
                                                                                  is("Palaparthi")),
                                                                     hasProperty("gender", is("Male")),
                                                                     hasProperty(
                                                                                  "DOB",
                                                                                  is(cal.getTime())),
                                                                     hasProperty("email",
                                                                                  is("v.p@gmail.com")),
                                                                     hasProperty("mobileNumber",
                                                                                  is("8121157247")),
                                                                     hasProperty("address", is("Huda")),
                                                                     hasProperty("courses",
                                                                                  hasItems("Math", "Chem"))))))
                           .andExpect(
                                         model().attribute(
                                                       "studentList",
                                                       hasItem(allOf(
                                                                     hasProperty("firstName",
                                                                                  is("Viswanath")),
                                                                     hasProperty("lastName",
                                                                                  is("Palaparthi")),
                                                                     hasProperty("gender", is("Male")),
                                                                     hasProperty(
                                                                                  "DOB",
                                                                                  is(cal.getTime())),
                                                                     hasProperty("email",
                                                                                  is("v.p@gmail.com")),
                                                                     hasProperty("mobileNumber",
                                                                                  is("8121157248")),
                                                                     hasProperty("address", is("Huda")),
                                                                     hasProperty("courses", hasItems("Math"))))));
 
              Mockito.verify(stuClient, Mockito.times(1)).getAllCourses();
              Mockito.verify(stuClient, Mockito.times(1)).getAllStudents(Mockito.any());
              Mockito.verifyNoMoreInteractions(stuClient);
 
       }
}